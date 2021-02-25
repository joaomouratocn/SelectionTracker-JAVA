package br.com.programadorjm.projectselectiontracker;


public class Model{
    private final int id;
    private final String title;

    private Model(ModelBuilder modelBuilder) {
        this.id = modelBuilder.id;
        this.title = modelBuilder.title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static class ModelBuilder{
        private int id;
        private String title;

        private ModelBuilder() {}

        public static ModelBuilder modelBuilder() { return new ModelBuilder();}

        public ModelBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public ModelBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Model build(){
            return new Model(this);
        }
    }
}
