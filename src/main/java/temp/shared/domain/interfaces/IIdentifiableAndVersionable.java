package temp.shared.domain.interfaces;

public interface IIdentifiableAndVersionable {

    /**
     * Gets the entity's id
     * @return Entity's id
     */
    int id();

    /**
     * Gets the Entity's info version
     * @return Entity's info version
     */
    long version();
}
