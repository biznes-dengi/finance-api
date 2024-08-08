import {RoutingProvider} from './RoutingProvider.tsx';
import {QueryClientProvider} from './QueryClientProvider.tsx';

export function AppProvider() {
	return (
		<QueryClientProvider>
			<RoutingProvider />
		</QueryClientProvider>
	);
}
