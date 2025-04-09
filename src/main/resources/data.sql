INSERT INTO color_palette (
    id,
    personal_color,
    description,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES (
             1,
             'SPRING',
             'You shine with bright and lively colors! You have a vibrant and energetic image, perfect for a Spring Warm Tone.',
             NOW(),
             NOW(),
             1,
             1
         );

INSERT INTO color_palette_colors (palette_id, color_code) VALUES (1, 'Peach');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (1, 'Coral');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (1, 'Ivory');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (1, 'Light Yellow');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (1, 'Salmon Pink');

-- SUMMER
INSERT INTO color_palette (
    id,
    personal_color,
    description,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES (
             2,
             'SUMMER',
             'Soft and cool pastel tones suit you well! You have a clear and elegant image, perfect for a Summer Cool Tone.',
             NOW(),
             NOW(),
             1,
             1
         );

INSERT INTO color_palette_colors (palette_id, color_code) VALUES (2, 'Lavender');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (2, 'Grayish Blue');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (2, 'Soft Pink');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (2, 'Pastel Tones');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (2, 'Pastel Tones');

-- FALL
INSERT INTO color_palette (
    id,
    personal_color,
    description,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES (
             3,
             'FALL',
             'Deep and calm colors are your charm! You have a luxurious and composed image, perfect for a Fall Warm Tone.',
             NOW(),
             NOW(),
             1,
             1
         );

INSERT INTO color_palette_colors (palette_id, color_code) VALUES (3, 'Camel');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (3, 'Brown');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (3, 'Khaki');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (3, 'Mustard');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (3, 'Terracotta');

-- WINTER
INSERT INTO color_palette (
    id,
    personal_color,
    description,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES (
             4,
             'WINTER',
             'You look amazing in bold, high-saturation cool tones! You have a crisp and striking image, perfect for a Winter Cool Tone.',
             NOW(),
             NOW(),
             1,
             1
         );

INSERT INTO color_palette_colors (palette_id, color_code) VALUES (4, 'Pure White');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (4, 'Black');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (4, 'Vivid Blue');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (4, 'Bright Purple');
INSERT INTO color_palette_colors (palette_id, color_code) VALUES (4, 'Cool Pink');
