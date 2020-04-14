package com.foureyes.data;

/**
 * An enumeration listing all necessary player's statistics,
 * which will be sent to FES's control cloud. 
 */
public enum PlayerStatistic {

	/**
	 * The number of blocks that have been broken.
	 */
	BLOCKS_BREAK, 
	/**
	 * The number of blocks that have been placed.
	 */
	BLOCKS_PLACED, 
	/**
	 * The number of mobs that have been killed.
	 */
	MOBS_KILLED,
	/**
	 * The number of players that have been killed.
	 */
	PLAYERS_KILLED,
	/**
	 * The current experience.
	 */
	EXP, 
	/**
	 * The number of experiences that need to level up.
	 */
	EXP_LEVEL_UP,
	/**
	 * The current XP level.
	 */
	LEVEL;
	
}
