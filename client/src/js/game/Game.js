import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import MotivationalStairsGame from './component/MotivationalStairs.js';

import MSAPI from './MotivationalStairsAPI.js';
import { dummyScores } from './scores.js';

import '../../css/App.css';



class Game extends React.Component {
    state = {
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

    constructor() {
        super();
        var scope = this;
        this.API = new MSAPI(scope.updateState);
        this.API.getUser();
        this.API.getGames();
        this.API.getHighScores();
    };

    updateState = (newState) => {
        console.log("new State:", newState);
        this.setState(newState);
    };

    render() {
        return (
            <MuiThemeProvider>
                <MotivationalStairsGame
                    model={this.state}
                >
                </MotivationalStairsGame>
            </MuiThemeProvider>
        );
    };
}
;

export default Game;
