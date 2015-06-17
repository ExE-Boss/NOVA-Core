package nova.core.retention;

import nova.core.util.ReflectionUtil;

/**
 * Classes with this interface declare ability to store and load itself.
 * Therefore, classes using this interface must have an empty constructor for new instantiation from load.
 */
public interface Storable {

	/**
	 * Saves all the data of this object.
	 * See {@link Data} for what data is storable.
	 *
	 * The default implementation saves all fields tagged with @Storable.
	 *
	 * @param data The data object to put values in.
	 */
	default void save(Data data) {
		ReflectionUtil.forEachRecursiveAnnotatedField(Store.class, getClass(), (field, annotation) -> {
			try {
				field.setAccessible(true);
				String name = annotation.key();
				if (name.isEmpty()) {
					name = field.getName();
				}
				data.put(name, field.get(this));
				field.setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	default void load(Data data) {
		ReflectionUtil.forEachRecursiveAnnotatedField(Store.class, getClass(), (field, annotation) -> {
			String name = annotation.key();
			if (name.isEmpty()) {
				name = field.getName();
			}
			if (data.containsKey(name)) {
				try {
					field.setAccessible(true);
					Class<?> type = field.getType();
					Object fieldValue = field.get(this);
					Object value = data.get(name);
					if (Storable.class.isAssignableFrom(type) || value instanceof Data) {
						if (fieldValue instanceof Storable && value instanceof Data) {
							//We already have an instance. Don't need to create the object.
							((Storable) fieldValue).load((Data) value);
						} else {
							field.set(this, Data.unserialize((Data) value));
						}
					} else {
						field.set(this, value);
					}
					field.setAccessible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
