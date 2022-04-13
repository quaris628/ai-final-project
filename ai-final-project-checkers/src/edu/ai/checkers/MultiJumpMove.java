
// I'm less sure this class is correct. -Nathan
public class MultiJumpMove extends Move {
	
	private JumpMove[] jumps;
	
	public static CreateMultiJumpMove(Piece piece, DiagonalDirection[] directions) {
		JumpMove[] jumps = new JumpMove[directions.length];
		Tile tile = piece.getTile();
		for (int i = 0; i < directions.length; i++) {
			jumpedTile = tile.getNeighborAt(directions[i]);
			destTile = jumpedTile.getNeighborAt(directions[i]);
			jumps[i] = new JumpMove(piece, jumpedTile, destTile);
			tile = destTile;
			if (tile == null) { return null; }
		}
		return MultiJumpMove(piece, tile, jumps);
	}
	
	private MultiJumpMove(Piece piece, Tile destination, JumpMove[] jumps) {
		super(piece, destination);
		this.jumps = jumps
	}
	
	@Override
	public boolean isValid() {
		for (JumpMove jump : jumps) {
			if (!jump.isValid()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void executeChild() {
		// TODO
	}
	
}