package cutiedev.webserver.web.view;

import cutiedev.webserver.web.response.IActionResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Connects a Html Document and a view of the same name or with a specific name given
 */
public abstract class View implements IActionResult
{
    private static final String HTML_EXTENSION = ".html";
    private static File file;


    public View()
    {
        try {
            setHtmlFile();
        } catch (URISyntaxException | FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void setHtmlFile() throws URISyntaxException, FileNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(getHtmlPath());

        // the stream holding the file content
        if (resource == null) {
            throw new FileNotFoundException("file not found! " + getHtmlPath());
        }
        File file = new File(resource.toURI());
    }

    private String getHtmlPath()
    {
        return  "html/" + this.getClass().getSimpleName() + HTML_EXTENSION;
    }
}
