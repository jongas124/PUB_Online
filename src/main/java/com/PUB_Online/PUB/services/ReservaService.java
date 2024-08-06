package com.PUB_Online.PUB.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.controllers.dtos.ReservaCreateDTO;
import com.PUB_Online.PUB.exceptions.HorarioException;
import com.PUB_Online.PUB.exceptions.InvalidNumberException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.exceptions.ReservaException;
import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.models.HorarioFuncionamento;
import com.PUB_Online.PUB.models.HorarioFuncionamento.DiasSemana;
import com.PUB_Online.PUB.models.Mesa;
import com.PUB_Online.PUB.models.Reserva;
import com.PUB_Online.PUB.repositories.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaService mesaService;

    @Autowired
    private HorarioFuncionamentoService horarioFuncionamentoService;

    @Autowired
    private ClienteService clienteService;

    public Reserva create(String cpf, Reserva reserva) {
        Reserva newReserva = new Reserva();
        Cliente cliente = this.clienteService.findByCpf(cpf);
        for (int i = 0; i < cliente.getReservas().size(); i++) {
            if (cliente.getReservas().get(i).getDataReserva().isEqual(reserva.getDataReserva())) {
                throw new ReservaException("Cliente já possui uma reserva para a data fornecida");
            }
        }
        newReserva.setCliente(cliente);
        newReserva.setDataReserva(reserva.getDataReserva());
        newReserva.setHoraAberturaReserva(reserva.getHoraAberturaReserva());
        newReserva.setNumeroPessoas(reserva.getNumeroPessoas());
        newReserva.setMesa(this.findMesaDisponivel(reserva.getDataReserva(), reserva.getHoraAberturaReserva(), reserva.getNumeroPessoas()));
        this.reservaRepository.save(newReserva);
        Mesa mesa = this.mesaService.findById(newReserva.getMesa().getNumero());
        mesa.getReservas().add(newReserva);
        this.mesaService.saveMesa(mesa);
        cliente.getReservas().add(newReserva);
        this.clienteService.saveCliente(cliente);
        return newReserva;
    }

    public List<Reserva> findAll() {
        return this.reservaRepository.findAll();
    }

    public Reserva findById(Long id) {
        if (!this.reservaRepository.findById(id).isPresent()) {
            throw new ObjectNotFoundException("Reserva não encontrada");
        }
        return this.reservaRepository.findById(id).get();
    }

    public List<Reserva> findByCpf(String cpf) {
        Cliente cliente = this.clienteService.findByCpf(cpf);
        return cliente.getReservas();
    }

    public void delete(Long id, String cpf) {
        Reserva reserva = this.findById(id);
        if (!reserva.getCliente().getCpf().equals(cpf)) {
            throw new ObjectNotFoundException("Reserva não encontrada");
        }
        this.reservaRepository.deleteByNumero(id);    
    }

    public void deleteAdmin(Long id) {
        this.reservaRepository.deleteByNumero(id);
    }

    public Mesa findMesaDisponivel(LocalDate data, LocalTime hora, Integer pessoas) {
        // Validações
        if (pessoas < 1 || pessoas == null) {
            throw new InvalidNumberException("Numero de pessoas invalido fornecido");
        }
        LocalDate hojeMais7 = LocalDate.now().plusDays(7);
        if (data.isBefore(hojeMais7)) {
            throw new ReservaException("A reserva deve ser feita com no mínimo 7 dias de antecedencia");
        }
        int codigoDia = data.getDayOfWeek().getValue();
        DiasSemana diaSemana = DiasSemana.fromCode(codigoDia);
        HorarioFuncionamento obj = horarioFuncionamentoService.findByDiaSemana(diaSemana);
        if(hora.isBefore(obj.getHoraAbertura()) || hora.isAfter(obj.getHoraFechamento())) {
            throw new HorarioException("O horario fornecida está fora do horário de funcionamento");
        }
        List<Mesa> mesas = this.mesaService.findAll();
        for(int i = 0; i < mesas.size(); i++) {
            Mesa mesa = mesas.get(i);
            if(mesa.getCapacidade() >= pessoas) {
                List<Reserva> reservas = mesa.getReservas();
                if (reservas.isEmpty()) {
                    return mesa;
                }
                for (int j = 0; j < reservas.size(); j++) {
                    if(!reservas.get(j).getDataReserva().isEqual(data)) {
                        return mesa;
                    }
                }
            }
        }
        throw new ReservaException("Não existem mesas disponíveis no dia selecionado ou não existem mesas que consigam acomodar o numero de pessoas fornecido");
    }

    public Reserva fromDTO (ReservaCreateDTO dto) {
        Reserva obj = new Reserva();
        obj.setDataReserva(dto.data());
        obj.setHoraAberturaReserva(dto.hora());
        obj.setNumeroPessoas(dto.pessoas());
        return obj;
    }
}