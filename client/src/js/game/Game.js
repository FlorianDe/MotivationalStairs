import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import MotivationalStairs from './component/MotivationalStairs.js';
import { dummyScores } from './scores.js';
import '../../css/App.css';



class Game extends React.Component {
    state = {
        user: {
            id: "1",
            name: "Thorsten Treppe"
        },

        games: [
            {
                id: 1,
                name: "Pong",
                subtitle: "Eines der ältesten Spiele, die es gibt!",
                description: "Pong is one of the first computer games that ever created, " +
                "this simple 'tennis like' game features two paddles and a ball, " +
                "the goal is to defeat your opponent by being the first one to gain10 point, " +
                "a player gets a point once the opponent misses a ball. " +
                "The game can be played with two human players, or one player against a computer controlled paddle.",
                gameModi: [
                    "Einzelspieler",
                    "Zwei Spieler"
                ],
                image: "images/pong.jpg"
            },
            {
                id: 2,
                name: "Tetris",
                subtitle: "Stapel die blöcke...",
                description: "Tetris ist ein puzzleartiges Computerspiel des russischen Programmierers Alexei Paschitnow, " +
                "der die erste spielbare Version am 6. Juni 1984 auf einem Elektronika-60-Rechner fertigstellte. " +
                "Tetris gilt heute als Computerspiel-Klassiker, " +
                "hat sich inzwischen über 100 Millionen Mal verkauft, wurde vielfach ausgezeichnet " +
                "und ist für mehr als 65 Computerplattformen erschienen",
                gameModi: [
                    "Einzelspieler",
                    "Zwei Spieler"
                ],
                image: "images/tetris.jpg"
            }
        ],

        highScores: dummyScores.sort(function(a,b) {
                        if (a.score > b.score)
                            return -1;
                        if (a.score < b.score)
                            return 1;
                        return 0;
                    })
    };


    render() {
        console.log(this.state.highScores);

        return (
            <MuiThemeProvider>
                <MotivationalStairs
                    games={this.state.games}
                    highScores={this.state.highScores}
                    user={this.state.user}
                >
                </MotivationalStairs>
            </MuiThemeProvider>
        );
    };
}
;

export default Game;
