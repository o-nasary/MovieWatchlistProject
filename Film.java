//Class that represents a Movie and it's given characteristics

public class Film {
    String filmName;
    String filmDirector;
    //String filmCategory;

    public Film(String filmName, String filmDirector) {
        this.filmName = filmName;
        this.filmDirector = filmDirector;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmDirector() {
        return filmDirector;
    }

    public void setFilmDirector(String filmDirector) {
        this.filmDirector = filmDirector;
    }
}