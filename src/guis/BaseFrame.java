package guis;

import javax.swing.*;


/*
    Creating an abstract class helps us set up the blueprint that our GUIs will follow, for example
    in each of the GUIs they will be the same size and will need to invoke their own addGuiComponents()
    which will be unique to each subclass
 */
public abstract class BaseFrame extends JFrame {

    public BaseFrame(String title){
        initialize(title);
    }

    private void initialize(String title){
        // instantiate JFrame properties and add a title to the bar
        setTitle(title);

        // set size  (in px)
        setSize(420, 600);


        // terminate the program when the gui is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // set layout to null to have absolute layout which allows us to manually specify the size and position
        // of each gui component
        setLayout(null);

        // prevent gui from being resized
        setResizable(false);

        //launch the gui in the center of the screen
        setLocationRelativeTo(null);

        // call on te subclass' addGuiComponents()
        addGuiComponents();
    }

    // this method will need to be defined by subclasses when this class is being inherited from
    protected abstract void addGuiComponents();
}


































