import {createBrowserRouter, Navigate, RouterProvider} from 'react-router-dom';

import {AppLayout} from '@pages/ui';

import {APP_PATH} from '@shared/config';
import {pageNotFoundRoute} from '@pages/not-found';
import {goalRoutes} from '@pages/goal';

const router = createBrowserRouter([
	{
		element: <AppLayout />,
		children: [...goalRoutes, pageNotFoundRoute],
	},

	{path: '*', element: <Navigate to={APP_PATH.pageNotFound} replace />},
]);

export function RoutingProvider() {
	return <RouterProvider router={router} />;
}
