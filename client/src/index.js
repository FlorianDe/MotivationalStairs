import React from 'react';
import ReactDOM from 'react-dom';
import Game from './js/game/Game.js';
import Configurator from './js/configurator/Configurator';
import injectTapEventPlugin from 'react-tap-event-plugin';
import './css/index.css';

injectTapEventPlugin();

function getApp() {
    return ((/^#configure*/gi.test(window.location.hash))?<Configurator/> :<Game />);
}

ReactDOM.render(
    getApp(),
    document.getElementById('root')
);
