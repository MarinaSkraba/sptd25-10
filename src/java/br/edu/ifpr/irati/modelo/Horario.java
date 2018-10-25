package br.edu.ifpr.irati.modelo;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "horario")
public class Horario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idHorario;

    @Temporal(TemporalType.TIME)
    private Date horaInicio;

    @Temporal(TemporalType.TIME)
    private Date horaTermino;

    @Column(name = "diaSemana", nullable = false, length = 15)
    private String diaSemana;

    @Column(name = "estadoHorario", nullable = false, length = 10)
    private String estadoHorario;

    @ManyToOne
    @JoinColumn(name = "professor_idUsuario")
    private Professor professor;

    public Horario() {

        this.idHorario = 0;
        this.horaInicio = new Time(0, 0, 0);
        this.horaTermino = new Time(0, 0, 0);
        this.diaSemana = "";
    }

    public Horario(int idHorario, Time horaInicio, Time horaTermino, String diaSemana, String estadoHorario, Professor professor) {
        this.idHorario = idHorario;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.diaSemana = diaSemana;
        this.estadoHorario = estadoHorario;
        this.professor = professor;
    }

    public int calcularCargaHoraria() {

        return 0;
        // rever isso

    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(Date horaTermino) {
        this.horaTermino = horaTermino;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getEstadoHorario() {
        return estadoHorario;
    }

    public void setEstadoHorario(String estadoHorario) {
        this.estadoHorario = estadoHorario;
    }
}
