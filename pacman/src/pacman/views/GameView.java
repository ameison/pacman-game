package pacman.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import pacman.model.Map;
import pacman.model.Monster;
import pacman.model.Pacman;
import pacman.model.StationaryObject;


public class GameView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final Dimension BLOCK_SIZE = new Dimension(24,24);
	private Dimension _gameDimensions;
	private Pacman _pacman;
	private JPanel _statusPanel;
	private JTextField txtNumeroPuntos = new JTextField();
	private JPanel _stationaryObjectsPanel;
	private JPanel _pacmanPanel;
	private JPanel _monstersPanel;
	private List<Monster> _monsters;

	/**
	 * Creates a new game view panel
	 * @param map game level map
	 */
	public GameView(Map map) {
		
		setGameMap(map);
		initializeUI();
	}
	
	/**
	 * Creates a new game using the given map
	 * @param map
	 */
	public void newGame(Map map) {
		setGameMap(map);
		initializeMap(map);
	}

	public void setStatus(String puntos){
		txtNumeroPuntos.setText(puntos);
	}
	
	/**
	 * set the "logical" pacman to be the "visual" pacman
	 * @param pacman
	 */
	public void setPacman(Pacman pacman) {
		// set the pacman to its default position
		Point oldPos = pacman.getPosition();
		
		if (_pacman != null) {
			oldPos = _pacman.getPosition();
			pacman.setDirection(_pacman.getDirection());
			_pacmanPanel.remove(_pacman);
			_pacmanPanel.repaint();
		}
		
		_pacman = pacman;
		_pacmanPanel.add(pacman);
		_pacman.setBounds(oldPos.x * BLOCK_SIZE.width, oldPos.y * BLOCK_SIZE.height, BLOCK_SIZE.width, BLOCK_SIZE.height);
	}

	/**
	 * Initialize the game map, and creates the stationary objects array which contains the visual objects, based on the given level map
	 * @param map
	 */
	private void initializeMap(Map map) {
		StationaryObject[][] objectsMap = map.getStationaryObjectsMap();
		_stationaryObjectsPanel.removeAll();

		for (int i = 0; i < objectsMap.length; i++) {
			for (int j = 0; j < objectsMap[i].length; j++) {
				_stationaryObjectsPanel.add(objectsMap[i][j]);
			}
		}
		
		_stationaryObjectsPanel.validate();
		_stationaryObjectsPanel.repaint();
	}
	
	/**
	 * Set the monsters to the view, connect the logic monsters with the visual monsters
	 * @param monsters
	 */
	public void setMonsters(List<Monster> monsters) {
		_monsters = monsters;
		
		_monstersPanel.removeAll();
		for (Monster m : _monsters) {
			_monstersPanel.add(m);
			m.setBounds(m.getPosition().x * BLOCK_SIZE.width, m.getPosition().y * BLOCK_SIZE.height, BLOCK_SIZE.width, BLOCK_SIZE.height);
		}
		_monstersPanel.validate();
	}

	/**
	 * Initialize the base UI for the game view
	 * Create the panels layers, and configure the visual properties
	 */
	private void initializeUI() {
		setBackground(Color.black);
		setSize(new Dimension(_gameDimensions.width * BLOCK_SIZE.width, _gameDimensions.height * BLOCK_SIZE.height));

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(getSize());
		GridLayout _panelsLayout = new GridLayout(_gameDimensions.height, _gameDimensions.width);
		
		// status panel
		_statusPanel = new JPanel();
		_statusPanel.setBounds (0,0,_gameDimensions.width * BLOCK_SIZE.width, 23);
		_statusPanel.setBackground(new Color(244, 124, 124));
		_statusPanel.setOpaque(true);
		_statusPanel.setLayout(new FlowLayout());
		JLabel lblPuntos = new JLabel("Puntaje: ");
		txtNumeroPuntos.setText("              ");
		txtNumeroPuntos.setSize(140, 25);
		_statusPanel.add(lblPuntos);
		_statusPanel.add(txtNumeroPuntos);
		layeredPane.add(_statusPanel);
		
		// pacman panel
		_pacmanPanel = new JPanel();
		_pacmanPanel.setOpaque(false);
		_pacmanPanel.setSize(getSize());
		_pacmanPanel.setLayout(null);  // absolute layout, pacman location is controlled by "setBounds" method
		layeredPane.add(_pacmanPanel);

		// pacman panel
		_pacmanPanel = new JPanel();
		_pacmanPanel.setOpaque(false);
		_pacmanPanel.setSize(getSize());
		_pacmanPanel.setLayout(null);  // absolute layout, pacman location is controlled by "setBounds" method
		layeredPane.add(_pacmanPanel);

		// stationary objects panel
		_stationaryObjectsPanel = new JPanel();
		_stationaryObjectsPanel.setOpaque(false);
		_stationaryObjectsPanel.setSize(getSize());
		_stationaryObjectsPanel.setLayout(_panelsLayout);
		layeredPane.add(_stationaryObjectsPanel, 1);

		// monsters panel
		_monstersPanel = new JPanel();
		_monstersPanel.setOpaque(false);
		_monstersPanel.setSize(getSize());
		_monstersPanel.setLayout(null); // absolute layout, pacman location is controlled by "setBounds" method
		layeredPane.add(_monstersPanel, 0);

		add(layeredPane);
	}

	public Dimension getGameDimensions() {
		return _gameDimensions;
	}

	public Pacman getPacman() {
		return _pacman;
	}
	
	private void setGameMap(Map map) {
		this._gameDimensions = map.getGameDimension();
	}
}
