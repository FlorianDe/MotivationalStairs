package de.motivational.stairs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Florian on 12.01.2017.
 */
@Component
public class PongConfig implements IUpdateable<PongConfig>{
    @Value("${app.constants.pong.ball.velocity}")
    public float pongBallVelocity;

    @Value("${app.constants.pong.ball.max.angle}")
    public float pongBallMaxAngle;

    @Value("${app.constants.pong.ball.radius}")
    public float pongBallRadius;

    @Value("${app.constants.pong.paddle.height}")
    public float pongPaddleHeight;

    @Value("${app.constants.pong.paddle.width}")
    public float pongPaddleWidth;

    @Value("${app.constants.pong.paddle.velocity}")
    public float pongPaddleVelocity;

    @Value("${app.constants.pong.tries}")
    public int pongTries;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PongConfig{");
        sb.append("pongBallVelocity=").append(pongBallVelocity);
        sb.append(", pongBallMaxAngle=").append(pongBallMaxAngle);
        sb.append(", pongBallRadius=").append(pongBallRadius);
        sb.append(", pongPaddleHeight=").append(pongPaddleHeight);
        sb.append(", pongPaddleWidth=").append(pongPaddleWidth);
        sb.append(", pongPaddleVelocity=").append(pongPaddleVelocity);
        sb.append(", pongTries=").append(pongTries);
        sb.append('}');
        return sb.toString();
    }

    public void update(PongConfig newConfig) {
        this.pongBallVelocity = newConfig.pongBallVelocity;
        this.pongBallMaxAngle = newConfig.pongBallMaxAngle;
        this.pongBallRadius = newConfig.pongBallRadius;
        this.pongPaddleHeight = newConfig.pongPaddleHeight;
        this.pongPaddleWidth = newConfig.pongPaddleWidth;
        this.pongPaddleVelocity = newConfig.pongPaddleVelocity;
        this.pongTries = newConfig.pongTries>0?newConfig.pongTries:1;
    }
}
