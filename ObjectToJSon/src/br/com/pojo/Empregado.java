package br.com.pojo;

/**
 *
 * @author wilson
 */
public class Empregado {

    private Long id;
    private String name;
    private Double salario;

    public Empregado(Long id, String name, Double salario) {
        this.id = id;
        this.name = name;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

}
