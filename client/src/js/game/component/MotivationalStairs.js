import React from "react";
import AppBar from 'material-ui/AppBar';
import FlatButton from 'material-ui/FlatButton';
import Accessibility from 'material-ui/svg-icons/action/accessibility';

const styles = {
    color: "white",
    height: 46
}


class MotivationalStairsGame extends React.Component {

    render() {
        return <AppBar
            showMenuIconButton={true}
            title="MoSt"
            iconElementLeft={<Accessibility style={styles}/>}
            iconElementRight={
                <div>
                    <FlatButton label="Spiele" style={styles}/>
                    <FlatButton label="Highscores" style={styles}/>
                </div>
            }
        />
    }
}

export default MotivationalStairsGame;