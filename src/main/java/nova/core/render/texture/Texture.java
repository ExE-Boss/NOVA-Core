package nova.core.render.texture;

import nova.core.util.Identifiable;
import nova.core.util.math.Vector2DUtil;
import nova.internal.core.Game;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * A texture has a file location. All texture must be included in
 * /assets/domain/textures/*
 *
 * @author Calclavia
 */
public class Texture extends Icon implements Identifiable {

	public final String domain;
	public final String resource;
	public final Vector2D dimension;

	@SuppressWarnings("deprecation")
	public Texture(String domain, String resource) {
		this.domain = domain;
		this.resource = resource;
		this.dimension = Game.render().getDimension(this);

		super.texture = this;
		super.minUV = Vector2DUtil.ONE;
		super.maxUV = Vector2D.ZERO;
	}

	public String getResource() {
		return domain + ":" + resource;
	}

	public String getPath() {
		return resource + ".png";
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + getPath() + "]";
	}

	@Override
	public final String getID() {
		return getResource();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == getClass()) {
			Texture other = (Texture) obj;
			return other.getID().equals(getID());
		}

		return false;
	}
}
