# Better Health TODO

Last updated: 2026-04-14 UTC

## Requested In This Round

- [ ] Make sure health text does not overlap hunger or saturation bars.
- [ ] Keep health bar height adjustable for live tuning.
- [ ] Re-check health bar border rendering, especially the missing-looking bottom edge report.
- [ ] Confirm the heart icon stays at the left side of the health text.
- [ ] Keep threshold slider supporting `0` and label `0` as `Always`.
- [ ] Add a dedicated adjustment/tuning tab with sliders for rapid in-game calibration.
- [ ] Add enough temporary positioning controls that defaults can be tuned later and reset back to `0`.

## Layout Modes To Support

- [ ] Half hunger bar mode:
- [ ] Display text without colliding with bars.
- [ ] Show saturation on the upper line and hunger on the lower line.
- [ ] Keep health visually separate from the hunger/saturation block.
- [ ] Full hunger bar mode:
- [ ] Center the health text cleanly.
- [ ] Render hunger/saturation and health as separate readable rows.
- [ ] Half health bar mode:
- [ ] Support split row rendering with health segment, configurable gap, and hunger segment.

## Split-Mode Controls

- [ ] Health percentage slider for split half mode.
- [ ] Gap percentage slider for split half mode.
- [ ] Left/right segment swap option.
- [ ] Hunger above/below health option for stacked modes.

## Alignment And Flow Controls

- [ ] Top anchor for both bars.
- [ ] Top health only anchor.
- [ ] Top hunger only anchor.
- [ ] Left, center, and right alignment.
- [ ] Configurable health fill direction.
- [ ] Floating number flow direction up/down.
- [ ] Mirror floating-number sideways drift based on HUD side/alignment.

## Notes On Current State

- Several requested controls already exist in the config screen and renderer.
- The main likely gaps are text spacing/tuning controls and validating the final visual layouts in-game.
- After adding the tuning tab, use it to calibrate values before deciding which controls should remain permanent.
