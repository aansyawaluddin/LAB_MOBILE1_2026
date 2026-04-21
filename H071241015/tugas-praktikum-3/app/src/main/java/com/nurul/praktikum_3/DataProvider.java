package com.nurul.praktikum_3;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    public static List<Book> bookList = new ArrayList<>();

    static {
        // Fiction
        bookList.add(new Book("1", "The Great Gatsby", "F. Scott Fitzgerald", "1925",
                "Sebuah kisah tragis tentang Jay Gatsby yang terobsesi untuk meraih kembali cinta lamanya, Daisy Buchanan, di tengah kemewahan dan kekosongan era Jazz Age tahun 1920-an di Long Island.",
                R.drawable.book1, 4.5, "Fiction",
                "Klasik sastra Amerika yang memukau dengan prosa indah dan kritik tajam terhadap American Dream."));

        bookList.add(new Book("2", "To Kill a Mockingbird", "Harper Lee", "1960",
                "Melalui mata Scout Finch yang masih kecil, novel ini mengisahkan perjuangan ayahnya, Atticus Finch, membela seorang pria kulit hitam yang dituduh memperkosa seorang wanita kulit putih di Alabama tahun 1930-an.",
                R.drawable.book2, 4.8, "Fiction",
                "Mahakarya yang mengharukan tentang keadilan, empati, dan keberanian moral. Wajib dibaca!"));

        bookList.add(new Book("3", "Dilan: Dia adalah Dilanku Tahun 1990", "Pidi Baiq", "2014",
                "Kisah cinta remaja SMA yang unik dan penuh kejutan antara Milea dan Dilan di kota Bandung pada tahun 1990. Dilan dengan caranya yang nyentrik berhasil mencuri hati Milea.",
                R.drawable.book3, 4.7, "Romance",
                "Sangat romantis dan ikonik. Dialog Dilan yang legendaris membuat novel ini tak terlupakan."));

        // Dystopian
        bookList.add(new Book("4", "1984", "George Orwell", "1949",
                "Di negara Oceania yang totaliter, Winston Smith memberanikan diri untuk berpikir dan mencintai secara bebas melawan pengawasan Big Brother yang mengontrol setiap aspek kehidupan warganya.",
                R.drawable.book4, 4.7, "Dystopian",
                "Novel dystopian paling berpengaruh sepanjang masa. Konsep Big Brother masih sangat relevan hingga kini."));

        bookList.add(new Book("5", "Brave New World", "Aldous Huxley", "1932",
                "Di dunia masa depan, manusia diciptakan di laboratorium dan dikondisikan untuk menerima peran sosial mereka. Bernard Marx mulai mempertanyakan kebahagiaan artifisial masyarakatnya.",
                R.drawable.book5, 4.4, "Dystopian",
                "Visi menakutkan tentang masa depan yang dikontrol oleh kesenangan dan konsumerisme."));

        bookList.add(new Book("6", "Fahrenheit 451", "Ray Bradbury", "1953",
                "Di masa depan di mana buku dilarang dan dibakar oleh pemadam kebakaran, Guy Montag mulai mempertanyakan tugasnya dan menemukan kekuatan transformatif dari literatur.",
                R.drawable.book6, 4.3, "Dystopian",
                "Peringatan kuat tentang bahaya sensor dan pentingnya kebebasan berpikir."));

        // Classic
        bookList.add(new Book("7", "Pride and Prejudice", "Jane Austen", "1813",
                "Elizabeth Bennet dan Mr. Darcy harus mengatasi prasangka dan keangkuhan masing-masing sebelum menyadari bahwa mereka saling mencintai, di tengah tekanan sosial Inggris era Regency.",
                R.drawable.book7, 4.6, "Classic",
                "Novel romantis klasik yang sempurna. Karakter Elizabeth Bennet adalah salah satu herorin terbaik dalam sastra."));

        bookList.add(new Book("8", "Jane Eyre", "Charlotte Brontë", "1847",
                "Kisah seorang gadis yatim piatu yang tumbuh menjadi wanita mandiri dan kuat, menemukan cinta dengan Mr. Rochester, namun harus menghadapi rahasia gelap yang tersembunyi di Thornfield Hall.",
                R.drawable.book8, 4.5, "Classic",
                "Karya feminis awal yang kuat dengan narasi yang mendalam dan penuh emosi."));

        bookList.add(new Book("9", "Wuthering Heights", "Emily Brontë", "1847",
                "Kisah cinta yang penuh gairah dan destruktif antara Heathcliff dan Catherine Earnshaw di dataran berbukit Yorkshire yang terpencil dan penuh badai.",
                R.drawable.book9, 4.2, "Classic",
                "Novel gothic yang gelap dan intens. Hubungan Heathcliff-Catherine adalah salah satu yang paling kompleks dalam sastra."));

        bookList.add(new Book("10", "Moby Dick", "Herman Melville", "1851",
                "Kapten Ahab yang terobsesi memimpin kapal penangkap ikan paus Pequod dalam pengejaran mematikan terhadap paus putih raksasa, Moby Dick, yang pernah merenggut kakinya.",
                R.drawable.book10, 4.1, "Classic",
                "Epik laut yang monumental. Eksplorasi mendalam tentang obsesi, alam, dan kondisi manusia."));

        // More Dystopian
        bookList.add(new Book("11", "The Hunger Games", "Suzanne Collins", "2008",
                "Di negara Panem yang dystopian, Katniss Everdeen secara sukarela menggantikan adiknya untuk bertarung hingga mati dalam Hunger Games, sebuah pertunjukan televisi yang kejam.",
                R.drawable.book11, 4.5, "Dystopian",
                "Thriller yang menegangkan dan tidak bisa dilepaskan. Katniss adalah pahlawan wanita yang menginspirasi."));

        // Sci-Fi
        bookList.add(new Book("12", "Dune", "Frank Herbert", "1965",
                "Paul Atreides, pewaris muda bangsawan, harus bertahan hidup di planet gurun Arrakis yang berbahaya sambil menavigasi intrik politik dan menguasai kekuatan mistis yang tersembunyi.",
                R.drawable.book12, 4.6, "Sci-Fi",
                "Mahakarya fiksi ilmiah yang kompleks dan visioner. World-building yang luar biasa detail."));

        // Romance (Indonesian)
        bookList.add(new Book("13", "Laskar Pelangi", "Andrea Hirata", "2005",
                "Kisah inspiratif sepuluh anak dari keluarga miskin di Belitung yang berjuang mendapatkan pendidikan layak di sekolah Muhammadiyah yang hampir roboh, dipimpin oleh guru mereka yang luar biasa.",
                R.drawable.book13, 4.6, "Romance",
                "Novel Indonesia paling menginspirasi. Mengharukan sekaligus menghibur dengan humor yang segar."));

        bookList.add(new Book("14", "Bumi Manusia", "Pramoedya Ananta Toer", "1980",
                "Minke, seorang pribumi terdidik di era kolonial Belanda, jatuh cinta pada Annelies dan harus menghadapi ketidakadilan sistem kolonial yang menindas melalui kekuatan tulisan.",
                R.drawable.book14, 4.8, "Classic",
                "Karya sastra terbesar Indonesia. Pramoedya mampu menghidupkan sejarah dengan cara yang sangat personal dan menggugah."));

        // Fiction
        bookList.add(new Book("15", "The Catcher in the Rye", "J.D. Salinger", "1951",
                "Holden Caulfield, remaja 16 tahun yang baru saja dikeluarkan dari sekolahnya, berkelana di New York City selama tiga hari sambil bergulat dengan keterasingan dan kehilangan kepolosan.",
                R.drawable.book15, 4.3, "Fiction",
                "Suara generasi yang gelisah. Holden Caulfield menjadi simbol pemberontakan remaja yang abadi."));
    }
}
