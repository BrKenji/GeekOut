package vhirata.com.geekout.model

class Book {

    var id: Int = 0
    var bookName: String = ""
    var authorName: String = ""
    var genre: String = ""
    var pages: Int = 0

    constructor(bookName: String, authorName: String, genre: String, pages: Int) {
        this.bookName = bookName
        this.authorName = authorName
        this.genre = genre
        this.pages = pages
    }

    constructor()

}