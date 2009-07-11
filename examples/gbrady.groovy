def server = new CouchRest()

def db = server.databasex("bradybunch")

def id = 'greg'
def doc = db.get(id)

if (doc) {
   db.delete_doc(doc)
   println "deleted"
}
else {
   doc = [ _id:id, fullname:'Greg Brady', room:'Attic',
           sideburn_dimensions:[2,4,6],
           brothers:["bobby","peter"],
           stuff:[lamp:'lava',shades:true] ]
   db.save_doc(doc)
   println "created"
}

println doc
