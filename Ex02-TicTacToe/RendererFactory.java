/**
 * RendererFactory class-Factory that generates Renderer.
 */
public class RendererFactory {
    /**
     * Constants for the switch case.
     */
    private static final String VOID_RENDERER = "none";
    private static final String CONSOLE_RENDERER = "console";

    /**
     * empty constructor for RendererFactory.
     */
    public RendererFactory() {
    }

    /**
     * this method gets a string and size and generates the wanted Renderer.
     *
     * @param type - the string name of the wanted Renderer.
     * @param size - the size of the board.
     * @return the wanted Renderer object.
     */
    public Renderer buildRenderer(String type, int size) {
        Renderer renderer;
        switch (type) {
            case VOID_RENDERER:
                renderer = new VoidRenderer();
                return renderer;
            case CONSOLE_RENDERER:
                renderer = new ConsoleRenderer(size);
                return renderer;
            default:
                return null;
        }

    }
}