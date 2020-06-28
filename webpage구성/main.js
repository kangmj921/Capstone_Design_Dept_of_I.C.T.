var express = require('express')
var fs = require('fs')
var lineReader = require('line-reader')
var app = express()
var mysql = require('mysql')
var bodyParser = require('body-parser')
var connection = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password : '1q2w3e4r!',
    database : 'kwangkyun'
});
connection.connect()
app.use(bodyParser.urlencoded({ extended: false }))
 

// parse application/json
app.use(bodyParser.json())
/*
var values = []
lineReader.eachLine('location.txt',function(line,last){
  
   var x = line.split(']')[0].split(',')[0].substring(1).replace(/\s/gi,"")
   var y = line.split(']')[0].split(',')[1].substring(1).replace(/\s/gi,"")
   var lat = line.split(']')[1].split(',')[1].substring(1,10).replace(/\s/gi,"")
   var lon = line.split(']')[1].split(',')[0].substring(2,12).replace(/\s/gi,"")
   
   values.push([x,y,lat,lon])
   if(last == true){
       var sql = "INSERT INTO location (x,y,lat,lon) VALUES ?"
       connection.query(sql,[values],function(err,results){
         if(err){
             console.log(err)
         }  
       })
   }
})*/

 





app.set('view engine','ejs')
app.use(express.static('public'))
app.listen(3000,function() {
    console.log("start pot 3000")
})

app.get('/', function(req,res){
    
    res.render('index.ejs')
    
})
app.get('/about',function(req,res){
    res.render('about.ejs')
})

app.get('/find',function(req,res){
    connection.query('select * from map',function(err,results,fields){
        if(err){
            console.log(err)
        }
        
        res.render('find.ejs',{result : JSON.stringify(results)})
       
        
    })  
})
app.get('/loc1',function(req,res){
    connection.query('select * from loc4',function(err,results){
        if(err){
            console.log(err)
        }
        res.render('loc1.ejs',{result:results} )
    })
})
app.get('/loc2',function(req,res){
    connection.query('select * from loc2',function(err,results){
        if(err){
            console.log(err)
        }
        res.render('loc2.ejs',{result:results} )
    })
})
app.get('/loc3',function(req,res){
    connection.query('select * from loc3',function(err,results){
        if(err){
            console.log(err)
        }
        res.render('loc3.ejs',{result:results} )
    })
})
app.get('/loc4',function(req,res){
    connection.query('select * from loc1',function(err,results){
        if(err){
            console.log(err)
        }
        res.render('loc4.ejs',{result:results} )
    })
})
app.get('/loc5',function(req,res){
    connection.query('select * from loc5',function(err,results){
        if(err){
            console.log(err)
        }
        res.render('loc5.ejs',{result:results} )
    })
})
app.get('/loc6',function(req,res){
    connection.query('select * from loc6',function(err,results){
        if(err){
            console.log(err)
        }
        res.render('loc6.ejs',{result:results} )
    })
})


app.post('/path',function(req,res){
    var data = req.body.data;
    connection.query('select * from '+data,function(err,results){
        if(err){
            console.log(err)
        }
        res.send({result: results})
        
        
    })
})
app.get('/map',function(req,res){
    var list = [[0, 3], [1, 3], [2, 3], [2, 4], [2, 5], [2, 6], [2, 7], [1, 7], [1, 8], [1, 9], [1, 10], [1, 11], [2, 11], [3, 11], [4, 11], [5, 11], [6, 11], [7, 11], [8, 11], [9, 11], [10, 11], [11, 11], [12, 11], [13, 11], [14, 11], [15, 11], [16, 11], [17, 11], [18, 11], [19, 11], [20, 11], [21, 11], [22, 11], [23, 11], [24, 11], [24, 12], [24, 13], [24, 14], [24, 15], [24, 16], [24, 17], [24, 18], [25, 18], [25, 19], [25, 20], [24, 20], [23, 20], [22, 20], [22, 21], [21, 21], [20, 21], [20, 22], [20, 23], [20, 24], [21, 24], [21, 25], [22, 25], [22, 26], [22, 27], [23, 27], [23, 28], [23, 29], [23, 30], [23, 31], [23, 32], [23, 33], [22, 33], [22, 34], [22, 35], [22, 36], [21, 36]]
    
    for(var i=0; i<list.length; i++){
        var idx = String(list[i][0]);
        var idy = String(list[i][1]);
        
        
        connection.query('SELECT * FROM location WHERE x='+idx+' AND y='+idy,function(err,result){
            if(err){
                console.log(err)
            }
            
            connection.query('insert into loc6 (x,y,lat,lon) values ' + '(' + result[0].x + ',' + result[0].y + ',' + result[0].lat+ ',' + result[0].lon + ')',function(err,results){
             if(err){
                 console.log(err)
             }  
            })
            
           
        })
    }
    
    
})

// app.get('/map',function(req,res){
//     var list = [[37.5648325, 126.98996480000001], [37.56206484, 126.99041740000001], [37.56195267, 126.988947], [37.56179748, 126.98668310000001], [37.56115792, 126.9816014], [37.56090972, 126.98166859999999], [37.56040325, 126.9820328], [37.56056383, 126.98273219999999], [37.56091153, 126.98578789999999], [37.55659293, 126.98505120000002], [37.55455528, 126.9831145], [37.55082092, 126.9988289], [37.55128848, 127.00064509999999], [37.55531959, 127.00386110000001], [37.5590977, 127.00584520000001], [37.55645612, 127.0093844], [37.55482232, 127.01064939999999], [37.55385312, 127.01142279999999], [37.55548134, 127.01235919999999], [37.55385312, 127.01142279999999], [37.55482232, 127.01064939999999], [37.55645612, 127.0093844], [37.55892239, 127.0067558], [37.55999555, 127.005672], [37.56208373, 127.00646529999999], [37.56244003, 127.00264720000001], [37.55972716, 127.00285670000001], [37.5629078, 127.00119860000001], [37.56303216, 126.9984699], [37.56236792, 126.99596820000001], [37.56267721, 126.99308070000001], [37.56436928, 126.99285970000001], [37.5648325, 126.98996480000001]]
    
//     console.log(list.length);
//     for(var i=0; i<list.length; i++){
//       connection.query('insert into loc4 (lat,lon) values ' + '(' + list[i][0]+ ',' + list[i][1] + ')',function(err,results){
//         if(err){
//         console.log(err)
//         }  
//       })
            
           
        
//     }
    
    
// })
