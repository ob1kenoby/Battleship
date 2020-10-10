class SuperClassGetter {

    public Class getSuperClassByName(String name) throws ClassNotFoundException {
        Class byName = Class.forName(name);
        return byName.getSuperclass();
    }

    public Class getSuperClassByInstance(Object object) {
        Class byInstance = object.getClass();
        return byInstance.getSuperclass();
    }
}