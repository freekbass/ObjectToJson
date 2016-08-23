package br.com;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wilson
 */
public class ObjectToJson {

    public static String TXT = "txt";
    public static String CSV = "csv";
    public static String XML = "xml";

    public static void convert(Object o, OutputStream os, String type) {
        if (os == null) {
            Logger.getLogger(ObjectToJson.class.getName()).severe("Objeto outputStream, não pode ser Nulo.");
        } else {
            writeJson(ConverterObject.convert(o), os, type);
        }
    }

    public static void convert(List lstObject, OutputStream os, String type) {
        if (os == null) {
            Logger.getLogger(ObjectToJson.class.getName()).severe("Objeto outputStream, não pode ser Nulo.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(ConverterObject.convertArray((Collection) lstObject));
            writeJson(sb.toString(), os, type);
        }
    }

    public static void convert(Object o, OutputStream os) {
        convert(o, os, TXT);
    }

    public static void convert(List<Object> lstObject, OutputStream os) {
        convert(lstObject, os, TXT);
    }

    private static void writeJson(String result, OutputStream os, String type) {
        if (TXT.equals(type)) {
            try {
                os.write(result.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(ObjectToJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (CSV.equals(type)) {
            //A implementar
        } else if (XML.equals(type)) {
            //A implementar
        }
    }
}
