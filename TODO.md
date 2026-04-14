# Better Health TODO

## User-requested features to implement

- [x] Fix text overlapping hunger/saturation bars.
- [x] Fix missing bottom edge of health bar border.
- [x] Keep health bar height adjustable (temporary while tuning).
- [x] Fix health bar rendering issue in current build.
- [x] Trigger threshold supports 0, and show `Always` for value `0` in config UI.
- [x] Ensure heart marker/icon appears at the left of health text/bar readout.

## Layout modes and visual behavior

- [x] Half hunger bar mode layout:
  - text line (`11.5/30 +12.3`)
  - saturation on top bar line
  - hunger on bottom bar line
  - health line under hunger/saturation block
- [x] Full hunger bar mode layout:
  - centered text line
  - full-width hunger/saturation line
  - health line under it
- [x] Half health bar mode layout:
  - centered text line
  - split row: health section + configurable gap + hunger section

## Split half-mode configurables

- [x] Configurable health percentage in split half-mode.
- [x] Configurable gap percentage in split half-mode.
- [x] Support examples like `30% health + 50% hunger + 20% gap`.

## Extra configurables requested

- [x] Swap hunger-under-health mode option (hunger above/below health).
- [x] Swap left/right option for split ordering.
- [x] Top-align mode (Halo-like) for health bar.
- [x] Configurable anchor: top for both, top-only hunger, or top-only health.
- [x] Horizontal alignment options: left, center, right.
- [x] Configurable health fill direction.
- [x] Floating number flow direction (up/down) configurable.
- [x] Rotate/mirror floating-number S-curve based on left/right placement.
