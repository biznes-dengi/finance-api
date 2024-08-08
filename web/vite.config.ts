import path from 'path';
import {defineConfig} from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [react()],
	resolve: {
		alias: {
			'@app': path.resolve('src/app'),
			'@pages': path.resolve('src/pages'),
			'@widgets': path.resolve('src/widgets'),
			'@features': path.resolve('src/features'),
			'@entities': path.resolve('src/entities'),
			'@shared': path.resolve('src/shared'),
		},
	},
});
