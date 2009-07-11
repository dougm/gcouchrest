def server = new CouchRest()

println server.info['couchdb'] + ", CouchDB version: " + server.info['version']

println "Databases..."

server.databases.each { name ->
    def db = server.database(name)
    println "...${name}"

    println "....Info"
    db.info.each { k,v ->
        println "......${k}=${v}"
    }

    println "....Docs"
    db.documents.each { k,v ->
        println "......${k}=${v}"
    }
}
