/** @type {import('tailwindcss').Config} */
export default {
	content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
	theme: {
		extend: {
			colors: {
				'primary-grey': '#75808A',
				'secondary-grey': '#DFE3E7',
				'light-grey': '#F7F7F7',
				'primary-violet': '#805CF5',
				'secondary-violet': '#F2EEFE',
				'primary-blue': '#4F55F1',
				'input-grey': '#EDEFF2',
				black: '#191C1F',
			},
		},
	},
	plugins: [],
};
