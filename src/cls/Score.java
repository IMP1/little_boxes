package cls;

import lvl.Levels;

public class Score {
	
	private int levelUpTo;
	private LevelSolution[] solutions;

	public Score() {
		levelUpTo = 1;
		solutions = new LevelSolution[Levels.LEVEL_COUNT];
		for (int i = 0; i < solutions.length; i ++) {
			solutions[i] = null;
		}
	}
	
	public int levelUpTo() {
		return levelUpTo;
	}
	
	public LevelSolution solution(int level) {
		if (solutions[level] == null) return null;
		return solutions[level];
	}
	
	public int moves(int level) {
		if (solutions[level] == null) return -1;
		return solutions[level].actions.length;
	}
	
	public void completeLevel(int level, LevelSolution solution) {
		// Increase the level we're up to
		levelUpTo = Math.max(levelUpTo, level + 1);
		// Better the solution if we can
		if (solutions[level] == null || moves(level) > solution.moves()) {
			solutions[level] = solution;
		}
	}

}
