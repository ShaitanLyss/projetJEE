import { startStimulusApp } from '@symfony/stimulus-bridge';
import PlacesAutocomplete from 'stimulus-places-autocomplete';
import ScrollTo from 'stimulus-scroll-to';
import ScrollReveal from 'stimulus-scroll-reveal';
import ReadMore from 'stimulus-read-more'
import Reveal from 'stimulus-reveal-controller'

// Registers Stimulus controllers from controllers.json and in the controllers/ directory
export const app = startStimulusApp(require.context(
    '@symfony/stimulus-bridge/lazy-controller-loader!./controllers',
    true,
    /\.(j|t)sx?$/
));

// register any custom, 3rd party controllers here
// app.register('some_controller_name', SomeImportedController);
app.register('places', PlacesAutocomplete)
app.register('scroll-to', ScrollTo)
app.register('scroll-reveal', ScrollReveal)
app.register('read-more', ReadMore)
app.register('reveal', Reveal)
