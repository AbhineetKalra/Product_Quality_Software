package com.nyu.edu.pqs.canvas.impl;

import com.nyu.edu.pqs.canvas.api.CanvasListener;
import com.nyu.edu.pqs.canvas.impl.CanvasInformation.ColorsAllowed;
import com.nyu.edu.pqs.canvas.impl.CanvasInformation.Mode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class is one possible thread unsafe implementation of the <code>CanvasListener</code>. This
 * class defines the GUI of the application and adds the various components required for the
 * application such as buttons and JPannel.
 * 
 * @author abhineet
 */
public class CanvasView implements CanvasListener {
  // found this the only possible place to implement Builder or Factory pattern but both of them
  // seemed to be a bad choice as there is only one view and the view object does not have many
  // arguments to use a Builder Pattern.
  private CanvasPanel canvas;
  private ModelImplementation modelReference;
  private JFrame mainWindow;
  private CanvasInformation.Mode currentMode;
  // for test only
  boolean reset = false;

  /**
   * Creates a new view to the canvas application.
   * 
   * @param modelToRegister Require the model to be registered with.
   */
  public CanvasView(ModelImplementation modelToRegister) {
    modelReference = modelToRegister;
    registerListener();
    currentMode = Mode.DRAW;
    canvas = new CanvasPanel();
  }

  /**
   * Starts the GUI of the view. All the events can been seen and performed through this view.
   */
  void showMainApp() {
    mainWindow = new JFrame("Let Us Paint");
    mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    mainWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        deregisterListener();
      }
    });
    mainWindow.setSize(500, 500);
    mainWindow.setResizable(false);
    mainWindow.setLocationRelativeTo(null);
    mainWindow.setLayout(new BorderLayout());
    mainWindow.add(getButtonPannel(), BorderLayout.SOUTH);
    mainWindow.add(canvas, BorderLayout.CENTER);
    mainWindow.pack();
    mainWindow.setVisible(true);
  }

  /**
   * A modified Panel which will act as the drawing board for the view.
   * 
   * @author abhineet
   */
  class CanvasPanel extends JPanel {

    private static final long serialVersionUID = -8712521841748694601L;
    private int oldPositionX;
    private int oldPositionY;
    private Image currentImage;
    private Color currentColor;
    private Graphics2D currentGraphics;

    MouseAdapter mouseClickedAdapter = new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        modelReference.notifyMouseClicked(e.getX(), e.getY());
      }
    };
    MouseAdapter mouseDraggedAdapter = new MouseAdapter() {
      public void mouseDragged(MouseEvent e) {
        modelReference.notifyMouseDragged(e.getX(), e.getY());
      }
    };

    /**
     * creates a new CanvasPanel of the modified attributes. This will be the drawing board.
     */
    public CanvasPanel() {
      currentColor = Color.BLACK;
      addMouseListener(mouseClickedAdapter);
      addMouseMotionListener(mouseDraggedAdapter);
      currentGraphics = (Graphics2D) getGraphics();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
      return new Dimension(600, 300);
    }

    /*
     * The painting was getting cleared when the Jframe was minimized due to calling of paint
     * method() when the window was restored. Learnt how to use graphics2D from:
     * http://forum.codecall.net/topic/58137-java-mini-paint-program/
     */
    protected void paintComponent(Graphics g) {
      if (currentImage == null) {
        currentImage = createImage(getSize().width, getSize().height);
        currentGraphics = (Graphics2D) currentImage.getGraphics();
        resetCanvas();
      }
      g.drawImage(currentImage, 0, 0, null);
    }

    /**
     * Updates the current color of the Panel graphics
     * 
     * @param changedColor Color to be changed
     * @return the current changed color
     */
    private Color updateColor(Color changedColor) {
      currentGraphics.setColor(changedColor);
      return currentColor;
    }

    /**
     * updates currentColor to RED
     */
    private void redColor() {
      currentColor = Color.RED;
      updateColor(Color.RED);
    }

    /**
     * updates currentColor to BLACK
     */
    private void blackColor() {
      currentColor = Color.BLACK;
      updateColor(Color.BLACK);
    }

    /**
     * updates currentColor to BLUE
     */
    private void blueColor() {
      currentColor = Color.BLUE;
      updateColor(Color.BLUE);
    }

    /**
     * updates currentColor to GREEN
     */
    private void greenColor() {
      currentColor = Color.GREEN;
      updateColor(Color.GREEN);
    }

    /**
     * updates currentColor to YELLOW
     */
    private void yellowColor() {
      currentColor = Color.YELLOW;
      updateColor(Color.YELLOW);
    }

    /**
     * updates currentColor to CYAN
     */
    private void cyanColor() {
      currentColor = Color.CYAN;
      updateColor(Color.CYAN);
    }

    /**
     * updates currentColor to MAGENTA
     */
    private void magentaColor() {
      currentColor = Color.MAGENTA;
      updateColor(Color.MAGENTA);
    }

    /**
     * updates currentColor to WHITE
     */
    private void whiteColor() {
      currentColor = Color.WHITE;
      updateColor(Color.WHITE);
    }

    /**
     * resets the canvas with a clear drawing board.
     */
    private void resetCanvas() {
      currentGraphics.setColor(Color.WHITE);
      currentGraphics.fillRect(0, 0, getSize().width, getSize().height);
      currentGraphics.setColor(currentColor);
      repaint();
    }
  }

  /**
   * @return a panel which contains all the buttons including the color buttons and the eraser and
   *         reset button
   */
  private JPanel getButtonPannel() {
    JPanel buttonPannel = new JPanel(new FlowLayout());
    buttonPannel.setPreferredSize(new Dimension(500, 50));
    JPanel eraserPanel = new JPanel();
    eraserPanel.setPreferredSize(new Dimension(100, 30));
    JButton eraser = new JButton("Eraser");
    eraser.setPreferredSize(new Dimension(80, 20));
    eraser.addActionListener(eraserButtonsListener());
    eraserPanel.add(eraser);
    buttonPannel.add(eraserPanel);
    JButton black = new JButton();
    black.setBackground(Color.BLACK);
    black.setPreferredSize(new Dimension(50, 30));
    black.addActionListener(getColorButtonsListener());
    buttonPannel.add(black);
    JButton red = new JButton();
    red.setBackground(Color.RED);
    red.setPreferredSize(new Dimension(50, 30));
    red.addActionListener(getColorButtonsListener());
    buttonPannel.add(red);
    JButton blue = new JButton();
    blue.setBackground(Color.BLUE);
    blue.setPreferredSize(new Dimension(50, 30));
    blue.addActionListener(getColorButtonsListener());
    buttonPannel.add(blue);
    JButton green = new JButton();
    green.setBackground(Color.GREEN);
    green.setPreferredSize(new Dimension(50, 30));
    green.addActionListener(getColorButtonsListener());
    buttonPannel.add(green);
    JButton yellow = new JButton();
    yellow.setBackground(Color.YELLOW);
    yellow.setPreferredSize(new Dimension(50, 30));
    yellow.addActionListener(getColorButtonsListener());
    buttonPannel.add(yellow);
    JButton cyan = new JButton();
    cyan.setBackground(Color.CYAN);
    cyan.setPreferredSize(new Dimension(50, 30));
    cyan.addActionListener(getColorButtonsListener());
    buttonPannel.add(cyan);
    JButton magenta = new JButton();
    magenta.setBackground(Color.MAGENTA);
    magenta.setPreferredSize(new Dimension(50, 30));
    magenta.addActionListener(getColorButtonsListener());
    buttonPannel.add(magenta);
    JPanel resetPanel = new JPanel();
    resetPanel.setPreferredSize(new Dimension(100, 30));
    JButton reset = new JButton("Reset");
    reset.setPreferredSize(new Dimension(70, 20));
    reset.addActionListener(resetButtonListener());
    resetPanel.add(reset);
    buttonPannel.add(resetPanel);
    return buttonPannel;
  }

  /**
   * @return Listener to reset button notifying the model of click on reset.
   */
  private ActionListener resetButtonListener() {
    ActionListener resetListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        modelReference.notifyCanvasReset();
      }
    };
    return resetListener;
  }

  /**
   * @return Listener to color buttons notifying the model of click on those buttons
   */
  private ActionListener getColorButtonsListener() {
    ActionListener colorButtonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        JButton clickedButton = (JButton) arg0.getSource();
        modelReference.notifyDrawClicked();
        modelReference.notifyColorChange(colorToColorsAllowed(clickedButton.getBackground()));
      }
    };
    return colorButtonListener;
  }

  /**
   * @return Listener to eraser button notifying the model of click on eraser.
   */
  private ActionListener eraserButtonsListener() {
    ActionListener eraseButtonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        modelReference.notifyEraserClicked();
      }
    };
    return eraseButtonListener;
  }

  @Override
  public void colorChange(ColorsAllowed changedColor) {
    if (changedColor == ColorsAllowed.RED) {
      canvas.redColor();
    } else if (changedColor == ColorsAllowed.BLUE) {
      canvas.blueColor();
    } else if (changedColor == ColorsAllowed.GREEN) {
      canvas.greenColor();
    } else if (changedColor == ColorsAllowed.MAGENTA) {
      canvas.magentaColor();
    } else if (changedColor == ColorsAllowed.YELLOW) {
      canvas.yellowColor();
    } else if (changedColor == ColorsAllowed.CYAN) {
      canvas.cyanColor();
    } else if (changedColor == ColorsAllowed.BLUE) {
      canvas.blueColor();
    } else if (changedColor == ColorsAllowed.WHITE) {
      canvas.whiteColor();
    } else {
      canvas.blackColor();
    }
  }

  @Override
  public void resetCanvas() {
    // for test only
    reset = true;
    canvas.resetCanvas();
  }

  @Override
  public void mouseDragged(int newPositonX, int newPositionY) {
    if (currentMode == Mode.DRAW) {
      canvas.currentGraphics.drawLine(canvas.oldPositionX, canvas.oldPositionY, newPositonX,
          newPositionY);
    } else if (currentMode == Mode.ERASE) {
      canvas.currentGraphics.fillOval(canvas.oldPositionX - 25, canvas.oldPositionY - 25, 50, 50);
    }
    canvas.repaint();
    canvas.oldPositionX = newPositonX;
    canvas.oldPositionY = newPositionY;
  }

  @Override
  public void eraserMode() {
    currentMode = Mode.ERASE;
  }

  @Override
  public void mouseClicked(int oldPositionX, int oldPositionY) {
    if (currentMode == Mode.ERASE) {
      canvas.currentGraphics.fillOval(oldPositionX - 25, oldPositionY - 25, 50, 50);
    }
    canvas.oldPositionX = oldPositionX;
    canvas.oldPositionY = oldPositionY;
  }

  @Override
  public boolean registerListener() {
    return modelReference.addListener(this);
  }

  @Override
  public boolean deregisterListener() {
    return modelReference.removeListener(this);
  }

  @Override
  public void drawMode() {
    currentMode = Mode.DRAW;
  }

  /**
   * A private method for changing colors to colorsAllowed. If the color passed in the parameter is
   * not present in the ColorsAvailable enum then BLACK color will be returned.
   * 
   * @param colorToBeConverted the color which needs to be converted to ColorsAvailable enum
   * @return the converted Color.
   */
  private ColorsAllowed colorToColorsAllowed(Color colorToBeConverted) {
    if (colorToBeConverted == null) {
      throw new IllegalArgumentException("Null colors not allowed");
    }
    if (colorToBeConverted == Color.RED) {
      return ColorsAllowed.RED;
    } else if (colorToBeConverted == Color.BLUE) {
      return ColorsAllowed.BLUE;
    } else if (colorToBeConverted == Color.GREEN) {
      return ColorsAllowed.GREEN;
    } else if (colorToBeConverted == Color.MAGENTA) {
      return ColorsAllowed.MAGENTA;
    } else if (colorToBeConverted == Color.YELLOW) {
      return ColorsAllowed.YELLOW;
    } else if (colorToBeConverted == Color.CYAN) {
      return ColorsAllowed.CYAN;
    } else if (colorToBeConverted == Color.BLUE) {
      return ColorsAllowed.BLUE;
    } else if (colorToBeConverted == Color.WHITE) {
      return ColorsAllowed.WHITE;
    } else {
      return ColorsAllowed.BLACK;
    }
  }

  // for testing use only
  ModelImplementation getModelReference() {
    return modelReference;
  }

  // for testing use only
  Mode getCurrentMode() {
    return currentMode;
  }

  // for testing use only
  void setCurrentMode(Mode m) {
    currentMode = m;
  }

  // for testing use only
  Color getCurrentColor() {
    return canvas.currentColor;
  }

  // for testing use only
  ColorsAllowed testColorToColorsAllowed(Color colorToBeConverted) {
    return colorToColorsAllowed(colorToBeConverted);
  }

  // for testing use only
  int getOldX() {
    return canvas.oldPositionX;
  }

  // for testing use only
  int getOldY() {
    return canvas.oldPositionY;
  }
}
