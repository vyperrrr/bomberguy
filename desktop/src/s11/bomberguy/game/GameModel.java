package s11.bomberguy.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.GameSetup;
import s11.bomberguy.characters.Monster;
import s11.bomberguy.characters.Player;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;
import s11.bomberguy.PlayerControl;

import java.util.ArrayList;
import java.util.stream.IntStream;


public class GameModel extends Game {

	// Initialize with dummy data
	private Timer timer;
	private final GameSetup setup;
	private ArrayList<Player> players;
	private final ArrayList<PlayerControl> controls;
	private ArrayList<Monster> monsters;
	private ArrayList<Crate> crates;
	private ArrayList<Wall> walls;

	// Data passed by GUI
	public GameModel(ArrayList<PlayerControl> controls, GameSetup setup) {
		this.controls = controls;
		this.setup = setup;
	}

	@Override
	public void create() {
		// Initialize players
		players = new ArrayList<>();

		// Generate players
		IntStream.range(0, setup.getPlayerNum()).forEach(i -> players.add(new Player(
				50, 50, 200, 200, 100, controls.get(i))));

		setScreen(new GameScreen(this));
	}



	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	public ArrayList<Crate> getCrates() {
		return crates;
	}

	public void setCrates(ArrayList<Crate> crates) {
		this.crates = crates;
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public void setWalls(ArrayList<Wall> walls) {
		this.walls = walls;
	}
}
