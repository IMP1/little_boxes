package cls;

public class LevelSolution {
	
	public enum Action {
		UP, DOWN, LEFT, RIGHT
	}
	
	public final Action[] actions;

	public LevelSolution(Action[] actions) {
		this.actions = actions;
	}
	
	public int moves() {
		return actions.length;
	}

}
