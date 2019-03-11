package org.privatebytes.problem267.pieces;

import org.privatebytes.problem267.pieces.visitor.Visitable;

public abstract class AbstractChessPiece implements Visitable {
	private char type;
	private Point position;

	public AbstractChessPiece(char type, Point position) {
		this.type = type;
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public char getType() {
		return type;
	}

	@Override
	public String toString() {

		String pkName = this.getClass().getPackageName();
		return this.getClass().getName().replace(pkName + ".", "") + position.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractChessPiece other = (AbstractChessPiece) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
