public class Mahasiswa {
    private int id;
    private String nim = null;
    private String nama = null;
    

    public Mahasiswa(int inputId, String inputNim, String inputNama){
        this.id = inputId;
        this.nim = inputNim;
        this.nama = inputNama;
        

    }

    public int getId() {
        return id;
    }
    public String getNim(){
        return nim;
    }
    public String getNama(){
        return nama;
    }

 
}
    

