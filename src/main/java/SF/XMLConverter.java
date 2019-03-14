package SF;

import com.thoughtworks.xstream.XStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class XMLConverter
{
    public void Serialize(String fileName, Signal signal) throws IOException
    {
        XStream stream = new XStream();
        stream.alias("signal", Signal.class);
        stream.alias("value", Map.Entry.class);

        String xml = stream.toXML(signal);

        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(xml);
        fileWriter.close();
        printWriter.close();

    }

    public Signal Deserialize(String fileName) throws IOException
    {
        XStream stream = new XStream();
        stream.alias("signal", Signal.class);
        stream.alias("value", Map.Entry.class);

        byte[] encoded = Files.readAllBytes(Paths.get(fileName));

        Signal signal = (Signal)stream.fromXML(new String(encoded, Charset.defaultCharset()));

        return signal;
    }
}
