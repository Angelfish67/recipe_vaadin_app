import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_0 from 'Frontend/styles/main.css?inline';
import '@vaadin/tooltip/theme/lumo/vaadin-tooltip.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/icon/theme/lumo/vaadin-icon.js';
import '@vaadin/text-field/theme/lumo/vaadin-text-field.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/horizontal-layout/theme/lumo/vaadin-horizontal-layout.js';
import '@vaadin/button/theme/lumo/vaadin-button.js';
import 'Frontend/generated/jar-resources/buttonFunctions.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

injectGlobalCss($cssFromFile_0.toString(), 'CSSImport end', document);

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '4c2a0f9fcbbfd1c5cd79359525f9923555f4d84f767a891e7e77a7a501407983') {
    pending.push(import('./chunks/chunk-ed6ca442387e3f76cf8759c87bf7d88750553c219dd7cf3af325c3fe25e9c069.js'));
  }
  if (key === '18fcad6fc9391b4ce3f649785d59736c53325583bf5ae4dee8f3ba96ed2bd372') {
    pending.push(import('./chunks/chunk-67abf1debb45b444c9cebc286f8d922886d40305b3453108cbb4d4502286df14.js'));
  }
  if (key === 'c8ccbbfb4bb6d442079942fa49b3f9bb11355df14b2a56d47fe320a58d543fbe') {
    pending.push(import('./chunks/chunk-67abf1debb45b444c9cebc286f8d922886d40305b3453108cbb4d4502286df14.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}