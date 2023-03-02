package dad.CoreJuego.Elementos;

import java.io.File;

public class Life {

	double life;
	boolean isPj, killed = false;
	File file;

	public Life(boolean isPj) {
		this.isPj = isPj;

	}

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public void addLife() {
		life += 0.5;
	}

	public void subtractLife() {
		life -= 1;
	}

	public double showLife() {

		if (!killed) {

			if (life == 3) {
				return 3;
			} else if (life == 2) {
				return 2;
			}

			else if (life == 1) {
				return 1;
			} else if (life % 5 != 0) {

				if (life / 0.5 > 3) {
					return 2.5;
				} else if (life / 0.5 >= 3) {
					return 1.5;
				} else {
					return 0.5;
				}
			}
		}
		killed = true;
		return 0;
	}
}
