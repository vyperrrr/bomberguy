package s11.bomberguy.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;
import s11.bomberguy.GameSetup;
import s11.bomberguy.characters.Monster;
import s11.bomberguy.characters.Player;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.Collidables;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;


public class GameModel extends Game {

	// Initialize with dummy data
	private Timer timer;
	private final GameSetup setup;
	private ArrayList<Player> players;
	private ArrayList<Monster> monsters;
	private ArrayList<Crate> crates;
	private ArrayList<Wall> walls;
	private final ArrayList<PlayerControl> controls;

	// Data passed by GUI
	public GameModel(ArrayList<PlayerControl> controls, GameSetup setup) {
		this.controls = controls;
		this.setup = setup;
	}

	@Override
	public void create() {
		// Initialize players
		players = new ArrayList<>();
		monsters = new ArrayList<>();
		crates = new ArrayList<>();
		walls = new ArrayList<>();
		/*
		// For testing reasons
		Random random = new Random();

		// Generate players
		IntStream.range(0, setup.getPlayerNum()).forEach(i -> players.add(new Player(
				random.nextInt(500), random.nextInt(1200), 120, 120, 100, controls.get(i))));

		crates.add(new Crate(120, 0));
		 */

		this.generateMap1();

		// Add collidable objects to collidables list
		Collidables collidables = Collidables.getInstance();

		collidables.addCollidables(players);
		collidables.addCollidables(monsters);
		collidables.addCollidables(crates);
		collidables.addCollidables(walls);

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

	private void generateMap1(){
		//pixelSize: Change this and everything's (except bombs) size will change with it
		int px = 120;

		//the map is a 16x9 grid, 0:0 is the bottom left corner

		//generating walls

		int wallX = 0-px;
		int wallY = 0-px;

		//walls around the map

		for (int i = 0; i < 18; i++) {
			this.walls.add(new Wall(wallX, wallY, px));
			this.walls.add(new Wall(wallX, (0 + px*9), px));
			wallX += px;
		}

		wallY = 0;
		for (int i = 0; i < 9; i++) {
			this.walls.add(new Wall((0-px), wallY, px));
			this.walls.add(new Wall((px*16), wallY, px));
			wallY += px;
		}

		//walls inside the map

		wallX = px * 7;
		wallY = 0;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2; j++) {
				this.walls.add(new Wall(wallX, wallY, px));
				wallX += px;
			}
			wallX = px * 7;
			wallY += px*2;
		}

		wallX = px;
		wallY = px;

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				this.walls.add(new Wall(wallX, wallY, px));
				wallY += px * 2;
			}
			wallX += px * 2;
			wallY = px;

			if(i == 2){
				wallX = px * 10;
			}
		}

		//generating crates

		int crateX = 0;
		int crateY = 0;

		for (int i = 0; i < 2; i++) {
			if(i == 0){
				crateY = px * 2;

				for (int j = 0; j < 3; j++) {
					this.crates.add(new Crate(crateX, crateY, px));
					crateY += px * 2;
				}
			}else{
				crateX = px * 15;

				crateY = px * 2;

				for (int j = 0; j < 3; j++) {
					this.crates.add(new Crate(crateX, crateY, px));
					crateY += px * 2;
				}
				crateY = 0;
			}
		}

		crateX = px * 6;

		for (int i = 0; i < 2; i++) {
			if(i == 0){
				crateY = px * 1;

				for (int j = 0; j < 4; j++) {
					this.crates.add(new Crate(crateX, crateY, px));
					crateY += px * 2;
				}
			}else{
				crateX = px * 9;

				crateY = px * 1;

				for (int j = 0; j < 4; j++) {
					this.crates.add(new Crate(crateX, crateY, px));
					crateY += px * 2;
				}
				crateY = 0;
			}
		}

		crateX = px * 2;
		crateY = 0;

		for (int i = 0; i < 4; i++) {
			if(i == 2){
				crateX = px * 11;
			}

			for (int j = 0; j < 5; j++) {
				this.crates.add(new Crate(crateX, crateY, px));
				crateY += px * 2;
			}
			crateX += px * 2;
			crateY = 0;
		}

		//generate players

		this.players.add(new Player(0,0 , (int) (px* 0.8), (int) (px* 0.8), (int)(px * 1.2), controls.get(0)));
		this.players.add(new Player((px*15),0 , (int) (px* 0.8), (int) (px* 0.8), (int)(px * 1.2), controls.get(1)));

		if(setup.getPlayerNum() == 3){
			this.players.add(new Player(0,(px*8) , (int) (px* 0.8), (int) (px* 0.8), (int)(px * 1.2), controls.get(2)));
		}


	}
}
