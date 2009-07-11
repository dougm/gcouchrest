import static groovyx.net.http.ContentType.*
import groovyx.net.http.*

class CouchRest {
    def client

    CouchRest() {
        this('http://localhost:5984')
    }

    CouchRest(url) {
        client = new RESTClient(url, JSON)
    }

    def get(path) {
        try {
            client.get(path).data
        } catch (e) {
            if (e.response.status == 404)
               return null
            else
               throw e
        }
    }

    def put(path) {
        client.put(path)
    }

    def put(path, payload) {
        client.put(path + [body : payload])
    }

    def post(path, payload) {
        client.post(path + [body : payload])
    }

    def delete(params) {
        client.delete(params)
    }

    def getInfo() {
        get(path : '/')
    }

    def getDatabases() {
        get(path : '/_all_dbs')
    }

    def database(name) {
        new CouchRestDatabase(this, name)
    }

    //XXX is there a groovy flavor of ruby 'database!' ?
    def databasex(name) {
        try { create_db(name) } catch (e) { database(name) }
    } 

    def create_db(name) {
        def db = database(name)
        put(db.path)
        db
    }
}

class CouchRestDatabase {
    def server
    def name
    def path

    CouchRestDatabase(server, name) {
        this.server = server
        this.name = name
        this.path = [path : '/' + name]
    }

    private def path(u) {
        '/' + name + '/' + u
    }

    def getInfo() {
        server.get(path)
    }

    def getDocuments() {
        server.get([path : path('_all_docs')])
    }

    def save_doc(doc) {
        server.put([path : path(doc['_id'])], doc)
    }

    def delete_doc(doc) {
        server.delete([path : path(doc['_id']), query : [rev : doc['_rev']]])
    }

    def get(id) {
        server.get([path : path(id)])
    }
}
