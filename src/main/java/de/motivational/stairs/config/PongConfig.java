package de.motivational.stairs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Florian on 12.01.2017.
 */
@Component
public class PongConfig {
    @Value("${app.constants.pong.ball.velocity}")
    public float pongBallVelocity;

    @Value("${app.constants.pong.ball.max.angle}")
    public float pongBallMaxAngle;

    @Value("${app.constants.pong.paddle.height}")
    public float pongPaddleHeight;

    @Value("${app.constants.pong.paddle.width}")
    public float pongPaddleWidth;

    @Value("${app.constants.pong.paddle.velocity}")
    public float pongPaddleVelocity;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PongConfig{");
        sb.append("pongBallVelocity=").append(pongBallVelocity);
        sb.append(", pongPaddleHeight=").append(pongPaddleHeight);
        sb.append(", pongPaddleWidth=").append(pongPaddleWidth);
        sb.append(", pongPaddleVelocity=").append(pongPaddleVelocity);
        sb.append('}');
        return sb.toString();
    }
}
