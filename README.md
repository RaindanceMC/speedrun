# RaindanceMC/speedrun

- **`speedrun-module`** — Rain's Flexible Modules — Speedrun: A speedrun lobby: an advancement goal or a death policy picked from a compass's menu, a countdown that freezes every racer before a green block actually starts the clock, and a world that resets itself once the last racer has left. Used directly by chained-module too, which needs this module installed alongside it for the shared engine underneath both.
- **`speedrun-standalone`** — Rain's Speedrun: Rain's Speedrun, as a plugin of its own, Manhunt included. Nothing here but a build file and a descriptor: the speedrun module and the standard wrapper are shaded together, and that is the whole plugin. A server coming from RainsManhunt has its files moved in on the first start.
- **`speedrun-bundle`** — Rain's Speedrun Server: A speedrun server in one jar: the speedrun lobby, Manhunt as a game that lobby plays, the chat (with Manhunt's team chat), and the world tools (world gate, /w, /dim, worlds and seeds), with RainsCore installed alongside.
- **`speedrun-e2e`** — Rain's Speedrun — end-to-end scenarios: RainsSpeedrun, Manhunt included, played on a real Paper server by headless bots — every command word, menu page and button, setting, chat button, lobby state and Manhunt behaviour. The normal build only checks that each of them has a scenario (CoverageTest); scripts/e2e.sh plays them.

Part of [RaindanceMC](https://github.com/RaindanceMC); built on [RainsCore](https://github.com/RaindanceMC/RainsCore) and the shared parent in [framework](https://github.com/RaindanceMC/framework). Split out of `rains-flexible-modules` on 16.10.2026, history kept.

## Build

```
mvn install
```

Needs Java 25 and read access to `packages.tstieh.de` (server ids `packages-releases` / `packages-snapshots` in `~/.m2/settings.xml`).

## Plugins

- `speedrun-standalone/target/RainsSpeedrun-1.36.0.jar` — also published as `de.raindancer.modules:speedrun-standalone:1.36.0`
- `speedrun-bundle/target/RainsSpeedrunServer-0.19.0.jar` — also published as `de.raindancer.modules:speedrun-bundle:0.19.0`

## Versions

A module's version is a property in this repository's root `pom.xml`, a plugin's is `plugin.version` in its own pom. CI publishes every version that is not in the registry yet on the next push to master.
