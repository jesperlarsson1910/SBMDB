@import org.example.sbmdb.entity.dto.MovieSummaryDTO
@import org.example.sbmdb.filter.MovieFilter
@import org.springframework.data.domain.Page
@import gg.jte.Content

@param Page<MovieSummaryDTO> movies
@param MovieFilter filter

@template.layout(title = "Movies", content = @`

    <div class="page-header">
        <h1 class="page-title">Movies</h1>
        <span style="color: var(--text-muted); font-size: 0.85rem;">${movies.getTotalElements()} titles</span>
    </div>

    <form class="filter-bar" method="get" action="/">
        <label>
            <input type="text" name="title" placeholder="Title..." value="${filter.title() != null ? filter.title() : ""}"/>
        </label>
        <label>
            <input type="text" name="director" placeholder="Director..." value="${filter.director() != null ? filter.director() : ""}"/>
        </label>
        <label>
            <input type="number" name="releaseYearFrom" placeholder="Year from..." style="max-width: 130px;"
                   value="${filter.releaseFrom() != null ? String.valueOf(filter.releaseFrom().getYear()) : ""}"/>
        </label>
        <label>
            <input type="number" name="releaseYearTo" placeholder="Year to..." style="max-width: 130px;"
                   value="${filter.releaseTo() != null ? String.valueOf(filter.releaseTo().getYear()) : ""}"/>
        </label>
        <label>
            <input type="number" name="ratingMin" placeholder="Min rating..." step="0.1" style="max-width: 120px;"
                   value="${filter.ratingLow() != null ? String.valueOf(filter.ratingLow()) : ""}"/>
        </label>
        <label>
            <input type="number" name="ratingMax" placeholder="Max rating..." step="0.1" style="max-width: 120px;"
                   value="${filter.ratingHigh() != null ? String.valueOf(filter.ratingHigh()) : ""}"/>
        </label>
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="/" class="btn btn-ghost">Clear</a>
    </form>

    @if(movies.isEmpty())
        <div class="empty">
            <div class="empty-title">No movies found</div>
            <p>Try adjusting your search filters.</p>
        </div>
    @else
        <div class="grid">
            @for(MovieSummaryDTO movie : movies.getContent())
                <a href="/movies/${movie.id()}" style="text-decoration: none;">
                    <div class="card">
                        <div style="font-family: var(--font-heading), serif; font-size: 1.05rem; color: var(--text); margin-bottom: 0.3rem;">
                            ${movie.title()}
                        </div>
                        <div style="font-size: 0.78rem; color: var(--text-muted); margin-bottom: 0.75rem;">
                            ${String.join(", ", movie.directors())} · ${movie.releaseYear().getYear()}
                        </div>
                        <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 0.75rem; border-top: 1px solid var(--border);">
                            @if(movie.rating() != null)
                                <div style="display: flex; align-items: center; gap: 0.5rem;">
                                    <div style="position: relative; display: inline-block; font-size: 0.9rem; letter-spacing: 0.1em;">
                                        <span style="color: var(--border);">★★★★★</span>
                                        <span style="position: absolute; left: 0; top: 0; overflow: hidden; width: ${String.format("%.1f", movie.rating() / 5.0 * 100)}%;">
                                            <span style="color: var(--accent);">★★★★★</span>
                                        </span>
                                    </div>
                                    <span style="font-size: 0.8rem; color: var(--text-muted);">${String.format("%.1f", movie.rating())}</span>
                                </div>
                            @else
                                <span style="font-size: 0.75rem; color: var(--text-muted);">No ratings yet</span>
                            @endif
                        </div>
                    </div>
                </a>
            @endfor
        </div>

        <div class="pagination">
            @if(!movies.isFirst())
                <a href="?page=${movies.getNumber() - 1}&size=${movies.getSize()}">←</a>
            @endif

            @for(int i = 0; i < movies.getTotalPages(); i++)
                @if(i == movies.getNumber())
                    <span class="active">${i + 1}</span>
                @else
                    <a href="?page=${i}&size=${movies.getSize()}">${i + 1}</a>
                @endif
            @endfor

            @if(!movies.isLast())
                <a href="?page=${movies.getNumber() + 1}&size=${movies.getSize()}">→</a>
            @endif
        </div>
    @endif
`)