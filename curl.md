### curl request examples

> start a game  
`curl -s -X POST http://localhost:8080/game/start`

> forced start a new game  
`curl -s -X POST http://localhost:8080/game/new`

> register a player  
`curl -s -X POST -d '{"name":"John Doe"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/game/register`

> get a word  
`curl -s http://localhost:8080/game/word`

> get a list of registered players  
`curl -s http://localhost:8080/game/players`

> guess a letter in the word  
`curl -s -X PUT -d '{"name":"John Doe","letter":"e"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/game/move`