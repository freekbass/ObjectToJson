package br.com.test;

import br.com.ObjectToJson;
import br.com.pojo.Empregado;
import br.com.pojo.Pessoa;
import br.com.pojo.Telefone;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author wilson
 */
public class TestProject {

    public TestProject() {
    }

    @Test
    public void Test1() throws FileNotFoundException {
        Pessoa object = new Pessoa();
        object.setId(1L);
        List<Telefone> lst = new ArrayList();
        lst.add(new Telefone(55L, "48", "9666-9899"));
        lst.add(new Telefone(60L, "48", "9666-9899"));
        object.setTelefones(lst);

        List<String> lstString = new ArrayList<>();

        lstString.add("Alo.....Alo....ALo");
        lstString.add("adasdasdadsa");

        object.setDados(lstString);

        ObjectToJson.convert(object, new FileOutputStream("C:/teste1.txt"), ObjectToJson.TXT);
    }

    @Test
    public void Test2() throws FileNotFoundException {
        List<Empregado> lsEmpregado = new ArrayList();

        lsEmpregado.add(new Empregado(1L, "Wilson", 5.000));
        lsEmpregado.add(new Empregado(2L, "Ricardo", 2.000));
        lsEmpregado.add(new Empregado(3L, "João", 1.000));

        ObjectToJson.convert(lsEmpregado, new FileOutputStream("C:/teste1.txt"), ObjectToJson.TXT);

    }
}
