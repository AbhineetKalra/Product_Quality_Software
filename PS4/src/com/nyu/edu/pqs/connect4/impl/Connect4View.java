package com.nyu.edu.pqs.connect4.impl;

import com.nyu.edu.pqs.connect4.api.Connect4Listener;
import com.nyu.edu.pqs.connect4.api.Player;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ColorsAvailable;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ModesAvailable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class serves as the Graphical User Interface(GUI) for the user. It implements the
 * Connect4Listener which enables it to connect to the model of Connect4.
 * 
 * @author abhineet
 */
public class Connect4View implements Connect4Listener {
  private JFrame mainFrame;
  private JFrame startFrame;
  private JPanel gridPanel;
  private JPanel mainFrameTopPanel;
  private JLabel messageLabel;
  private Model modelRefernce;
  private ImageIcon blueIcon;
  private ImageIcon greenIcon;
  private JButton resetButton;
  private JButton gridButtons[][];

  public Connect4View(Model modelReference) {
    this.modelRefernce = modelReference;
    modelReference.addListener(this);
    blueIcon = new ImageIcon("resources//blue.jpg");
    greenIcon = new ImageIcon("resources//green.jpg");
    gridButtons = new JButton[GameInformation.ROWS][GameInformation.COLUMNS];
  }

  /**
   * This method provides an ActionListener to every button on the grid which makes it possible to
   * track which column user hits.
   * 
   * @return ActionListener to all the grid buttons
   */
  public ActionListener gridButtonActionListener() {
    ActionListener buttonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent gridButtonActionEvent) {
        JButton clickedButton = (JButton) gridButtonActionEvent.getSource();
        Integer columnNumber = (Integer) clickedButton.getClientProperty("Column");
        modelRefernce.fireMovePlayedEvent(columnNumber);
      }
    };
    return buttonListener;
  }

  /**
   * This method provides an ActionListener to the start screen buttons making it possible to track
   * the mode of the game chosen by the user.
   * 
   * @return Action listener to both the buttons on the start screen
   */
  public ActionListener startButtonListener() {
    ActionListener startListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent startButtonActionEvent) {
        GameInformation.ModesAvailable mode = null;
        JButton clickedButton = (JButton) startButtonActionEvent.getSource();
        if (clickedButton.getText().equalsIgnoreCase("Vs Human")) {
          Player p1 = PlayerFactory.getPlayer(true, "Player1", ColorsAvailable.BLUE);
          Player p2 = PlayerFactory.getPlayer(true, "Player2", ColorsAvailable.GREEN);
          modelRefernce.setPlayers(p1, p2);
          mode = ModesAvailable.MULTIPLAYER;
        } else if (clickedButton.getText().equalsIgnoreCase("Vs Computer")) {
          modelRefernce.setPlayers(PlayerFactory.getPlayer(true, "Player", ColorsAvailable.BLUE),
              PlayerFactory.getPlayer(false, "Computer", ColorsAvailable.GREEN));
          mode = ModesAvailable.SINGLEPLAYER;
        }
        startFrame.dispose();
        launchMainFrame();
        modelRefernce.fireGameStartedEvent(mode);
      }
    };
    return startListener;
  }

  /**
   * This method provides an ActionListener to keep a track of when user hits the reset button in
   * the game.
   * 
   * @return ActionListener to the reset button
   */
  public ActionListener resetButtonListener() {
    ActionListener resetListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        modelRefernce.fireResetGameEvent();
      }
    };
    return resetListener;
  }

  /**
   * This method provides a panel consisting of all the grid buttons.
   * 
   * @return Panel of JButtons
   */
  public JPanel gridPanel() {
    gridPanel = new JPanel(new GridLayout(GameInformation.ROWS, GameInformation.COLUMNS));
    for (int i = 0; i < GameInformation.ROWS; i++) {
      for (int j = 0; j < GameInformation.COLUMNS; j++) {
        JButton button = new JButton();
        button.putClientProperty("Column", j);
        button.setEnabled(false);
        button.setBackground(Color.WHITE);
        button.addActionListener(gridButtonActionListener());
        gridButtons[i][j] = button;
        gridPanel.add(button);
      }
    }
    return gridPanel;
  }

  /**
   * This method provides a panel consisting of a message label and reset button.
   * 
   * @return panel with a reset JButton and message Label
   */
  public JPanel MainFrameTopPannel() {
    mainFrameTopPanel = new JPanel(new BorderLayout());
    messageLabel = new JLabel("Welcome to Connect4! Please choose a mode to play.");
    resetButton = new JButton("Reset Game");
    resetButton.addActionListener(resetButtonListener());
    resetButton.setEnabled(false);
    mainFrameTopPanel.add(resetButton, BorderLayout.EAST);
    mainFrameTopPanel.add(messageLabel, BorderLayout.WEST);
    return mainFrameTopPanel;
  }

  /**
   * This method launches the Main Frame where the game will actually be played.
   */
  public void launchMainFrame() {
    mainFrame = new JFrame("Connet4");
    mainFrame.setSize(600, 500);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setResizable(false);
    mainFrame.setLayout(new BorderLayout());
    mainFrame.add(gridPanel(), BorderLayout.CENTER);
    mainFrame.add(MainFrameTopPannel(), BorderLayout.NORTH);
    mainFrame.setVisible(true);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Launches the start frame to ask for the mode of the game. This frame is followed by Main frame.
   */
  public void launchStartFrame() {
    startFrame = new JFrame("Choose Mode");
    startFrame.setSize(300, 150);
    startFrame.setResizable(false);
    startFrame.setLocationRelativeTo(null);
    startFrame.setLayout(new BorderLayout());
    JPanel startFrameBottomPanel = new JPanel(new GridLayout());
    JPanel startFrameTopPanel = new JPanel();
    JLabel startFrameImage = new JLabel(new ImageIcon("resources//title1.jpg"));
    startFrameTopPanel.add(startFrameImage);
    JButton computerButton = new JButton("Vs Computer");
    JButton humanButton = new JButton("Vs Human");
    computerButton.addActionListener(startButtonListener());
    humanButton.addActionListener(startButtonListener());
    startFrameBottomPanel.add(computerButton);
    startFrameBottomPanel.add(humanButton);
    startFrame.add(startFrameTopPanel, BorderLayout.NORTH);
    startFrame.add(startFrameBottomPanel, BorderLayout.SOUTH);
    startFrame.pack();
    startFrame.setVisible(true);
    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void gameStarted(ModesAvailable mode) {
    messageLabel.setText("Game Started In " + mode + " Mode!! Lets Play ");
    for (JButton[] buttonArray : gridButtons) {
      for (JButton button : buttonArray) {
        button.setEnabled(true);
      }
    }
    resetButton.setEnabled(true);
  }

  @Override
  public void nextPlayer(Player nextPlayer) {
    messageLabel.setText(nextPlayer.playerName() + "'s Turn");
  }

  @Override
  public void illeagleMove(String message) {
    messageLabel.setText(message);
  }

  @Override
  public void movePlayed(int row, int column, Player player) {
    if (player.playerColor() == ColorsAvailable.BLUE) {
      gridButtons[row][column].setIcon(blueIcon);
    } else {
      gridButtons[row][column].setIcon(greenIcon);
    }
  }

  @Override
  public void notifyWinner(Player winner) {
    JOptionPane.showMessageDialog(null, winner.playerName() + " has won the game");
  }

  @Override
  public void gameEnded() {
    for (JButton[] buttonRow : gridButtons) {
      for (JButton button : buttonRow) {
        button.setEnabled(false);
      }
    }
    messageLabel.setText("Please click 'Restart Game' button to play again.");
  }

  @Override
  public void resetGame() {
    for (JButton[] buttonRow : gridButtons) {
      for (JButton button : buttonRow) {
        button.setIcon(null);
        button.setEnabled(true);
      }
    }
    messageLabel.setText("Fresh Game Started");
  }

  @Override
  public void gameDraw() {
    JOptionPane.showMessageDialog(null, "Whoops! Game ended in a draw. Try Again!");
  }
}
