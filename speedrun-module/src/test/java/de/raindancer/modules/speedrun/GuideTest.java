package de.raindancer.modules.speedrun;

import de.raindancer.core.ui.help.HelpLint;
import de.raindancer.modules.api.ModuleCommand;
import de.raindancer.modules.speedrun.manhunt.ManhuntSettings;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class GuideTest {

    @Test
    void theGuideIsComplete() {
        assertThat(HelpLint.of(Path.of("src/main/resources/rains-help"))
                .module("speedrun")
                .settings(SpeedrunSettings.class, SpeedrunSettings.DEFAULTS)
                .settings(ManhuntSettings.class, ManhuntSettings.DEFAULTS)
                .commands(new SpeedrunModule().commands().stream().map(ModuleCommand::name).toList())
                .problems()).isEmpty();
    }
}
