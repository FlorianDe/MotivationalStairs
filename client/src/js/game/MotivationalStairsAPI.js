
var MSAPI = function(stateCallback) {
    var scope = this;

    scope.stateCallback = stateCallback;
    scope.state = {
        API: this,
        step: 0,
        isRegistered: -1,
        isGameSelect: true,
        user: {
            id: -1,
            name: "Anonym",
            cookie: null
        },
        games: [],
        opponents: [],
        queue: [],
        highscores: [],
        playRequest: {
            state: false,
            user: {
                id: -1,
                name: "Anonym"
            }
        },
        notification: {
            state: false,
            message: "",
            detail: "",
            callback: null
        },
        askedPlayer: 0,
        changeUserName: false,
        gameTicket: null
    };
};

MSAPI.prototype = {
    baseURL: window.location.protocol + "//" + window.location.hostname + ":8080/api/v1.0/",
    socket: null,

    getUser: function() {
        var scope = this;

        // TODO remove cookie in production code
        scope.get("user/me?uid="+scope.readCookie("ms_user_c"), function(user) {
            if(user !== null) {
                scope.updateState({ isRegistred: 1, user: user});
                scope.initSocket();
            } else {
                scope.post("user/", function(newUser) {
                    scope.updateState({ isRegistered: 1, user:newUser });
                    scope.createCookie("ms_user_c", newUser.cookie);
                    scope.initSocket();
                }, { });
            }
        });
    },

    refreshUserInfo: function() {
        var scope = this;
        scope.get("user/me?uid="+scope.readCookie("ms_user_c"), function(user) {
            if(user !== null) {
                scope.updateState({user: user});
            }
        });
    },

    setStep: function(step) {
        if(step === 3) {
            this.startGame();
        }
        this.updateState({step: step});
    },

    setGameSelect: function(isGameSelect) {
        this.updateState({isGameSelect: isGameSelect});
    },

    getGames: function() {
        var scope = this;

        scope.get("game/", function(games) {
            for(var id in games) {
                if(games.hasOwnProperty(id)) {
                    var game = games[id];
                    var modes = game.gameModes;
                    game.gameModes = [];
                    for(var i = 1; i <= modes; i++) {
                        switch(i) {
                            case 1:
                                game.gameModes.push("Einzelspieler");
                                break;
                            case 2:
                                game.gameModes.push("Zwei Spieler");
                                break;
                            default:
                                game.gameModes.push(i+" Spieler");
                        }
                    }
                }
            }
            scope.updateState ({ games: games });
        });
    },

    getOpponents: function(reset) {
        var scope = this;
        scope.get("user/active/", function(users) {
            var keys = {};
            scope.state.opponents.forEach(function(oldOpponent) {
                keys[oldOpponent.id] = oldOpponent;
            });
            users = users.map(function(newOpponent) {
                var oldOpponent = keys[newOpponent.id];
                if(oldOpponent && !reset) {
                    newOpponent.asked = oldOpponent.asked;
                } else {
                    newOpponent.asked = 0;
                }
                return newOpponent;
            });
            scope.updateState({ opponents: users });
        });
    },

    getHighScores: function() {
        var scope = this;
        scope.get("highscore/game/1", function(highscores) {
            scope.updateState(
                {
                    highscores: highscores.sort(function(a,b) {
                    if (a.highscore > b.highscore)
                        return -1;
                    if (a.highscore < b.highscore)
                        return 1;
                    return 0;
                })
            });
        });
    },

    askOpponentForPlay: function(userId) {
        var scope = this;
        var choosen = scope.state.opponents.filter(function(user) {
            if(user.id === userId)
                return true;
            else
                return false;
        });

        if(choosen.length === 1 && choosen[0].asked !== -2) {
            choosen[0].asked = -2;
            scope.state.askedPlayer = userId;
            scope.socket.send("U"+userId);
        } else {
            scope.putNotification("Anfrage nicht durchführbar", "Der Spieler hat auf deine letzte Anfrage noch nicht geantwortet");
        }
        scope.updateState({});
    },

    answerPlayRequest: function(state) {
        var scope = this;
        if(scope.state.playRequest.state) {
            scope.socket.send("A"+((state)?"T":"F")+scope.state.playRequest.user.id);
            scope.updateState({ playRequest: { state: false, user: scope.state.playRequest.user } });
            scope.putNotification("Warte noch kurz", "Warte bis "+scope.state.playRequest.user.name + " das Spiel startet");
        }
    },

    putNotification: function(message, detail, callback) {
        this.updateState({notification: { state: true, message: message, detail: detail, callback:callback }})
    },

    dismissNotification: function(state){
        if(this.state.notification.callback !== undefined && this.state.notification.callback !== null) {
            this.state.notification.callback((state !== undefined)?true:false);
        }
        this.updateState({notification: { state: false, message: "", detail: "", callback: null}});
    },

    openUserNameDialog: function() {
        this.updateState({ changeUserName: true });
    },

    closeUserNameDialog: function() {
        this.updateState({ changeUserName: false });
        this.refreshUserInfo();
    },

    setNewUserName: function(userName) {
        var scope = this;
        scope.updateState(
            {
                user: {
                    id: scope.state.user.id,
                    cookie: scope.state.user.cookie,
                    name: userName
                }
            });
    },

    saveNewUserName: function() {
        var scope = this;
        scope.put("user/", function(newUser) {
            if(!newUser) {
                scope.putNotification("Speichern fehlgeschlagen", "Ups, da ist etwas beim Speichern schief gelaufen...");
            } else {
                scope.refreshUserInfo();

            }
        }, scope.state.user);
        scope.closeUserNameDialog();
    },

    startGame: function() {
        var scope = this;
        scope.post("game/start/", function(gameTicket) {
            scope.updateState({ gameTicket: gameTicket });
        }, { gameId: 1, users: [ scope.state.user.id, scope.state.askedPlayer ] });
    },

    updateGameQueue: function() {
        var scope = this;
        scope.get("game/queue/", function(queue) {
            scope.updateState({queue: queue});
        });
    },

    redeemTicket: function() {
        var scope = this;
        if(scope.state.gameTicket !== null) {
            scope.get("game/redeem/"+scope.state.gameTicket.ticket, function(response) {

            });
        }
    },

    updateState: function(newState) {
        var scope = this;
        scope.state = Object.assign({}, scope.state, newState);
        scope.stateCallback(scope.state);
    },

    initSocket: function() {
        var scope = this;
        scope.socket = new WebSocket("ws://"+ window.location.hostname + ":8080/events");
        scope.socket.onopen = scope.socketOpen.bind(scope);
        scope.socket.onclose = scope.socketClose.bind(scope);
        scope.socket.onmessage = scope.socketMessage.bind(scope);
        scope.socket.onerror = scope.socketError.bind(scope);
    },

    socketOpen: function(message) {
        this.socket.send("R"+this.readCookie("ms_user_c"));
    },

    socketClose: function(message) {
        console.info("Close:",message);
    },

    socketMessage: function(message) {
        var scope = this;

        var payload = JSON.parse(message.data);
        var event = payload.event;
        var data;
        switch(event) {
            case "NEXT_TICKET":
                window.setTimeout(function() {
                    scope.redeemTicket();
                }, 200);
                break;
            case "TICKET_EXPIRED":
                scope.updateState({ gameTicket: null });
                break;
            case "QUEUE_CHANGED":
                console.log("GAME_QUEUED", payload.payload);
                scope.updateState({ gameTicket: payload.payload });
                scope.getHighScores();
                scope.updateGameQueue();
                break;
            case "NEW_PLAYER_LIST":
                scope.getOpponents();
                scope.getHighScores();
                break;
            case "SOCKET_ESTABLISHED":
                scope.getOpponents();
                break;
            case "PLAY_REQUEST":
                data = (payload.payload)?JSON.parse(payload.payload):"";
                scope.updateState({ playRequest: { state: true, user: data }});
                break;
            case "PLAY_RESPONSE":
                data = payload.payload;
                if(data.value) {
                    scope.updateState({askedPlayer: data.user.id});
                    scope.setStep(2);
                } else {
                    scope.putNotification(data.user.name+" hat geantwortet", "Nein danke ;)");
                }
                break;
            case "GAME_QUEUEING":
                console.log("GAME_QUEUEING", payload);
                break;
            case "GAME_STARTING":
                console.log("GAME_STARTING", payload.payload);
                if(payload.payload === 0) {
                    scope.putNotification("Spiel geht los!", "Viel erfolg!");
                } else {
                    scope.putNotification("Spiel geht los!", "Begeben Sie sich auf die Treppe, es bleiben Ihnen "+ payload.payload + " Sekunden");
                }
                break;
            case "GAME_OVER":
                scope.getHighScores();
                scope.putNotification("Du hast " + ((payload.payload === true)?"gewonnen!":"verloren :("), "Schau dir deinen Highscore an", function(val) {
                    if(val) {
                        scope.setGameSelect(false);
                    }
                });
                scope.updateState({askedPlayer: 0});
                scope.getOpponents(true);
                break;
            case "SOCKET_ERROR⁠⁠⁠⁠":
                console.error("SOCKET_ERROR",payload);
                break;
            default:
                console.warn("Unsed socket event", payload);
        }
    },

    sendMessage: function(message) {
        var scope = this;
        if(scope.socket !== null && scope.socket.readyState === scope.socket.OPEN) {
            scope.socket.send(message);
        } else {
            console.warn("Unable to send Message via socket!", message);
            // TODO socket wieder aufbauen oder was hier los?
        }
    },

    socketError: function(message) {
        console.error("Error:",message);
    },

    get: function(url, callback, options) {
        var scope = this;
        scope.request(scope.baseURL + url, "GET", options, callback);
    },

    post: function(url, callback, data, options) {
        var scope = this;
        if(options === undefined) {
            options = { headers: { "Content-Type": "application/json" }};
        }
        options.payLoad = data;
        scope.request(scope.baseURL + url, "POST", options, callback);
    },

    put: function(url, callback, data, options) {
        var scope = this;
        if(options === undefined) {
            options = { headers: { "Content-Type": "application/json" }};
        }
        options.payLoad = data;
        scope.request(scope.baseURL + url, "PUT", options, callback);
    },

    request: function(url, method, options, callback) {
        var req = new XMLHttpRequest();
        req.open(method.toUpperCase(), url);
        if(options !== undefined){
            for(var opt in options.headers) {
                if(options.headers.hasOwnProperty(opt)) {
                    req.setRequestHeader(opt, options.headers[opt]);
                }
            }
        }

        req.onreadystatechange = function() {
            if(req.readyState === XMLHttpRequest.DONE && req.status === 200) {
                if(callback !== undefined) {
                    callback((req.responseText!=="")?JSON.parse(req.responseText):"");
                }
            }
        };

        switch(method.toUpperCase()){
            case 'GET':
            case 'DELETE':
                req.send();
                break;
            case 'PUT':
            case 'POST':
                req.send(JSON.stringify(options.payLoad));
                break;
            default:
                console.warn("Unrecognized request method:", method);
        }
    },

    createCookie: function (name,value,days) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days*24*60*60*1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + value + expires + "; path=/";
    },

    readCookie: function(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)===' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length,c.length);
        }
        return null;
    }
};

export default MSAPI;