package savetheenvironment;

public class CatAdmin
{

    private String name;



    public Cat getCat() {
        return new Cat(this.name);
    }


    public void updateCat(String name) {
        this.name = name;

    }

}
