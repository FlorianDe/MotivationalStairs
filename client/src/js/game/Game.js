import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import MotivationalStairs from './component/MotivationalStairs.js';
import '../../css/App.css';

class Game extends Component {
  render() {
    return (
        <MuiThemeProvider>
          <MotivationalStairs >

          </MotivationalStairs>
        </MuiThemeProvider>
    );
  }
}

export default Game;
